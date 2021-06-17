package com.example.labelapi;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class LabelingJobProcessor {

    private static final String BINDING_FINISHED_JOB = "finish-out-0";

    private StreamBridge streamBridge;

    public LabelingJobProcessor(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Bean
    public Consumer<String> start() {
        return payload -> {
            System.out.println("Static consumer called ");
            System.out.println("Message: " + payload + " delivered");
            processLabelingJob(payload);
        };
    }

    public void processLabelingJob(String job) {

        System.out.println("Processing job : " + job);
        streamBridge.send(BINDING_FINISHED_JOB, "Finished");
    }
}
