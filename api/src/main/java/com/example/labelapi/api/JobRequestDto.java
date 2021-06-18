package com.example.labelapi.api;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequestDto {

    private long expiration;

    // TODO: Replace with json
    String input;
}
