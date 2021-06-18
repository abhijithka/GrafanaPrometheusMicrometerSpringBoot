package com.example.labelapi.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Jobs", description = "Labeling job related operations")
@RestController
@RequestMapping("/api/v1/jobs")
public class LabelingJobController {

    private LabelingJobService labelingJobService;

    public LabelingJobController(LabelingJobService labelingJobService) {
        this.labelingJobService = labelingJobService;
    }

    @Operation(summary = "Create a new labeling job")
    @PostMapping
    public ResponseEntity<Job> createJob(@Validated @RequestBody JobRequestDto jobRequest) {

        Job responseDTO = labelingJobService.createLabelingJob(jobRequest);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a job")
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable long id) {

        Job responseDTO = labelingJobService.getJob(id);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
