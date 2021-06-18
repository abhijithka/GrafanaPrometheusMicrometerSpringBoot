package com.example.labelapi.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobMetadataRepository extends MongoRepository<JobMetadata, String> {

    JobMetadata findById(long id);
    List<JobMetadata> findAllByStartTimeNotAndFinishTimeNot(LocalDateTime startTime, LocalDateTime finishTime);
}
