package com.personal_organization.infrastructure.mongo.repository;


import com.personal_organization.infrastructure.mongo.entity.ProjectTypeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectTypeMongoRepository
        extends MongoRepository<ProjectTypeDocument, String> {

    List<ProjectTypeDocument> findAllByActiveTrue();
}

