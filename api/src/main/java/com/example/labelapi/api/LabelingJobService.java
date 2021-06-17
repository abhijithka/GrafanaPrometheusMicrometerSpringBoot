package com.example.labelapi.api;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class LabelingJobService {

    private static final String BINDING_CREATE_JOB = "create-out-0";
    private IdGeneratorService idGenerator;
    private LabelingJobRepository labelingJobRepository;
    private StreamBridge streamBridge;

    public LabelingJobService(IdGeneratorService idGenerator,
                               LabelingJobRepository labelingJobRepository,
                              StreamBridge streamBridge) {
        this.idGenerator = idGenerator;
        this.labelingJobRepository = labelingJobRepository;
        this.streamBridge = streamBridge;
    }

    public Job createLabelingJob(Job jobRequest) {

        Job job = Job.builder()
                .id(idGenerator.getNewJobId())
                .build();
        labelingJobRepository.save(job);
        streamBridge.send(BINDING_CREATE_JOB, "job created");
        return  job;
    }

    public Job getJob(long jobId) {
        return labelingJobRepository.findById(jobId);
    }

    @Bean
    public Consumer<String> finish() {
        return payload -> {
            System.out.println("Message: " + payload + " delivered");
        };
    }
}
