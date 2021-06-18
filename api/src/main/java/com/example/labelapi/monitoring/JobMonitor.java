package com.example.labelapi.monitoring;

import com.example.labelapi.api.*;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

@Service
public class JobMonitor {

    private final AtomicLong avgTurnaroundTime;
    private final Counter successCounter;
    private final Counter failureCounter;
    private MeterRegistry meterRegistry;
    private LabelingJobRepository jobRepository;
    private JobMetadataRepository metadataRepository;

    public JobMonitor(MeterRegistry meterRegistry,
                      LabelingJobRepository jobRepository,
                      JobMetadataRepository metadataRepository) {
        this.meterRegistry = meterRegistry;
        this.metadataRepository = metadataRepository;
        this.jobRepository = jobRepository;
        avgTurnaroundTime = meterRegistry.gauge("avg_turnaround_time", new AtomicLong(0));
        successCounter = meterRegistry.counter("success_count");
        failureCounter = meterRegistry.counter("failure_count");
    }

    @Bean
    public Consumer<Job> onStarted() {
        return job -> {
            JobMetadata jobMetadata = metadataRepository.findById(job.getId());
            jobMetadata.setStatus(JobStatus.EXECUTING);
            jobMetadata.setStartTime(LocalDateTime.now());
            metadataRepository.save(jobMetadata);
            jobRepository.save(job);
        };
    }

    private void updateAvgTurnAroundTime() {
        List<JobMetadata> completedJobMetadata = metadataRepository.findAllByStartTimeNotAndFinishTimeNot(null, null);
        OptionalDouble avgTime = completedJobMetadata.stream().mapToDouble(completedJob -> ChronoUnit.MILLIS.between(completedJob.getStartTime(), completedJob.getFinishTime())).average();
        avgTurnaroundTime.set(((long) avgTime.orElse(0) / 1000));
    }

    @Bean
    public Consumer<Job> onSuccess() {
        return job -> {
            JobMetadata jobMetadata = metadataRepository.findById(job.getId());
            jobMetadata.setStatus(JobStatus.SUCCEEDED);
            jobMetadata.setFinishTime(LocalDateTime.now());
            metadataRepository.save(jobMetadata);
            jobRepository.save(job);
            updateAvgTurnAroundTime();
            successCounter.increment();
        };
    }

    @Bean
    public Consumer<Job> onFailure() {
        return job -> {
            JobMetadata jobMetadata = metadataRepository.findById(job.getId());
            jobMetadata.setStatus(JobStatus.DISCARDED);
            jobMetadata.setFinishTime(LocalDateTime.now());
            metadataRepository.save(jobMetadata);
            jobRepository.save(job);
            updateAvgTurnAroundTime();
            failureCounter.increment();
        };
    }

    @Bean
    public Consumer<Job> onRetry() {
        return job -> {
            JobMetadata jobMetadata = metadataRepository.findById(Long.valueOf(job.getId()));
            jobMetadata.setStatus(JobStatus.EXECUTING);
            metadataRepository.save(jobMetadata);
            jobRepository.save(job);
        };
    }
}
