package com.example.labelapi.api;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class IdGeneratorService {

    public long getNewJobId() {

        // Todo: Improve the id logic to a more robust implementation
        return ZonedDateTime.now().toInstant().toEpochMilli();
    }
}
