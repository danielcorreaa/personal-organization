package com.personal_organization.infrastructure.mongo.mapper;


import com.personal_organization.domain.project.ProjectItem;
import com.personal_organization.domain.valueobject.*;
import com.personal_organization.infrastructure.mongo.entity.ProjectItemDocument;

public class ProjectItemDocumentMapper {

    private ProjectItemDocumentMapper() {}

    public static ProjectItemDocument toDocument(ProjectItem item) {
        ProjectItemDocument doc = new ProjectItemDocument();
        doc.setId(item.getId());
        doc.setProjectId(item.getProjectId().value());
        doc.setUserId(item.getUserId());
        doc.setName(item.getName());
        doc.setValue(item.getValue().getAmount());
        doc.setCategory(item.getCategory().value());
        doc.setCompleted(item.isCompleted());
        doc.setDescription(item.getDescription());
        return doc;
    }

    public static ProjectItem toDomain(ProjectItemDocument doc) {
        var project = new ProjectItem(
                doc.getId(),
                new ProjectId(doc.getProjectId()),
                doc.getUserId(),
                doc.getName(),
                new Money(doc.getValue()),
                new CategoryId(doc.getCategory()),
                doc.getDescription()
        );
        project.setCompleted(doc.isCompleted());
        return project;
    }
}
