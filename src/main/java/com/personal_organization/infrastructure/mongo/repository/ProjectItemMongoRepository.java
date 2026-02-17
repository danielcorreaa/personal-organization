package com.personal_organization.infrastructure.mongo.repository;

import com.personal_organization.infrastructure.mongo.entity.ProjectItemDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectItemMongoRepository extends MongoRepository<ProjectItemDocument, String> {
    List<ProjectItemDocument> findByProjectId(String projectId);


    void deleteAllByProjectId(String projectId);
}
