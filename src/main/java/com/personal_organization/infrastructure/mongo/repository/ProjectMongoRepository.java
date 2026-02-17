package com.personal_organization.infrastructure.mongo.repository;

import com.personal_organization.infrastructure.mongo.entity.ProjectDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMongoRepository
        extends MongoRepository<ProjectDocument, String> {

    Optional<ProjectDocument> findByIdAndUserId(String id, String userId);

    List<ProjectDocument> findAllByUserIdAndStatus(String userId, String status);
}
