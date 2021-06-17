package com.example.labelapi.api;

import org.springframework.stereotype.Service;

@Service
public class LabelingJobService {

    private IdGeneratorService idGenerator;
    private LabelingJobRepository labelingJobRepository;

    public LabelingJobService(IdGeneratorService idGenerator,
                               LabelingJobRepository labelingJobRepository) {
        this.idGenerator = idGenerator;
        this.labelingJobRepository = labelingJobRepository;
    }

    public Job createLabelingJob(Job jobRequest) {

        Job job = Job.builder()
                .id(idGenerator.getNewJobId())
                .build();
        labelingJobRepository.save(job);
        return  job;
    }

    public Job getJob(long jobId) {
        return labelingJobRepository.findById(jobId);
    }
}
