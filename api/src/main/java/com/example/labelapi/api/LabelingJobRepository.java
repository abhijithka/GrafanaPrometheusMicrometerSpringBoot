package com.example.labelapi.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelingJobRepository extends MongoRepository<Job, String> {

    Job findById(long id);
}
