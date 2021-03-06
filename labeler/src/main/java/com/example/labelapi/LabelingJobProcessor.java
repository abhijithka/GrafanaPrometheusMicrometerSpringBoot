package com.example.labelapi;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class LabelingJobProcessor {

    private static final String BINDING_JOB_STARTED = "started-out-0";
    private static final String BINDING_JOB_RETRIED = "retried-out-0";
    private static final String BINDING_JOB_SUCCEEDED = "succeeded-out-0";
    private static final String BINDING_JOB_FAILED = "failed-out-0";

    private StreamBridge streamBridge;

    public LabelingJobProcessor(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Bean
    public Consumer<Job> execute() {
        return job -> {
            processLabelingJob(job);
        };
    }

    private void processLabelingJob(Job job) {

        job.setStatus(JobStatus.EXECUTING);
        streamBridge.send(BINDING_JOB_STARTED, job);

        // Fail some job for testing
        if (job.getInput().length() == 9) {
            job.setStatus(JobStatus.DISCARDED);
            streamBridge.send(BINDING_JOB_FAILED, job);
            return;
        }

        String label = labelInput(job.getInput());
        job.setOutput(label);
        job.setStatus(JobStatus.SUCCEEDED);
        streamBridge.send(BINDING_JOB_SUCCEEDED, job);
    }

    // TODO: Improve the input/out and labeling logic
    private String labelInput(String input) {
        return "Output_" + input.length();
    }
}
