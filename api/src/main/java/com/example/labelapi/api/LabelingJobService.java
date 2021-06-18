package com.example.labelapi.api;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Service
public class LabelingJobService {

    private static final String BINDING_CREATE_JOB = "added-out-0";
    private IdGeneratorService idGenerator;
    private LabelingJobRepository labelingJobRepository;
    private JobMetadataRepository metadataRepository;
    private StreamBridge streamBridge;
    private MeterRegistry meterRegistry;
    private final Counter jobCounter;

    public LabelingJobService(IdGeneratorService idGenerator,
                              LabelingJobRepository labelingJobRepository,
                              JobMetadataRepository metadataRepository,
                              StreamBridge streamBridge,
                              MeterRegistry meterRegistry) {
        this.idGenerator = idGenerator;
        this.labelingJobRepository = labelingJobRepository;
        this.streamBridge = streamBridge;
        this.metadataRepository = metadataRepository;
        this.meterRegistry = meterRegistry;
        jobCounter = meterRegistry.counter("job_count");
    }

    public Job createLabelingJob(JobRequestDto jobRequest) {

        Job job = Job.builder()
                .id(idGenerator.getNewJobId())
                .status(JobStatus.AWAITING_FOR_SCHEDULING)
                .expiration(jobRequest.getExpiration())
                .context("")// TODO: Improve the context
                .input(jobRequest.getInput())
                .submittedAt(LocalDateTime.now())
                .build();
        Job savedJob = labelingJobRepository.save(job);
        JobMetadata jobMetadata = JobMetadata.builder()
                .id(savedJob.getId())
                .status(savedJob.getStatus())
                .build();
        metadataRepository.save(jobMetadata);
        streamBridge.send(BINDING_CREATE_JOB, job);
        jobCounter.increment();
        return  job;
    }

    public Job getJob(long jobId) {
        return labelingJobRepository.findById(jobId);
    }
}
