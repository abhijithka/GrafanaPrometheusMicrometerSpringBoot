package com.example.labelapi;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {

    @Id
    private long id;
    private JobStatus status;
    private long expiration;
    LocalDateTime submittedAt;

    // TODO: Replace with json
    String input;
    String output;
    String context;
}
