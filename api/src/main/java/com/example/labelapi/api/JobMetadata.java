package com.example.labelapi.api;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobMetadata {

    // Same as jobId for now, check again
    @Id
    private long id;
    private LocalDateTime startTime;
    private JobStatus status;
    private LocalDateTime finishTime;

}
