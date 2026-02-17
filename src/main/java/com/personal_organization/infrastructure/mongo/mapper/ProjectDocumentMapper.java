package com.personal_organization.infrastructure.mongo.mapper;


import com.personal_organization.domain.project.Project;
import com.personal_organization.domain.project.ProjectStatus;
import com.personal_organization.domain.project.SelectedCategory;
import com.personal_organization.domain.valueobject.Money;
import com.personal_organization.domain.valueobject.ProjectId;
import com.personal_organization.domain.valueobject.UserId;
import com.personal_organization.infrastructure.mongo.entity.ProjectDocument;
import com.personal_organization.infrastructure.mongo.entity.SelectedCategoryDocument;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDocumentMapper {

    public static Project toDomain(ProjectDocument doc) {
        if (doc == null) return null;

        return new Project(
                new ProjectId(doc.getId()),
                new UserId(doc.getUserId()),
                doc.getType(),
                doc.getTitle(),
                doc.getDate(),
                toMoney(doc.getBudget()),
                toStatus(doc.getStatus()),
                toSelectedCategoryDomain(doc.getSelectedCategories())
        );
    }

    public static ProjectDocument toDocument(Project domain) {
        if (domain == null) return null;

        ProjectDocument doc = new ProjectDocument();

        doc.setId(domain.getId().value());
        doc.setUserId(domain.getUserId().value());
        doc.setType(domain.getType());
        doc.setDate(domain.getDate());
        doc.setTitle(domain.getTitle());
        doc.setBudget(domain.getBudget().getAmount());
        doc.setStatus(domain.getStatus().name());
        doc.setSelectedCategories(toSelectedCategoryDocument(domain.getSelectedCategories()));

        return doc;
    }

    private static Money toMoney(BigDecimal value) {
        return value == null ? null : new Money(value);
    }

    private static ProjectStatus toStatus(String status) {
        return status == null ? null : ProjectStatus.valueOf(status);
    }

    private static List<SelectedCategory> toSelectedCategoryDomain(
            List<SelectedCategoryDocument> docs) {
        if (docs == null) return List.of();
        return docs.stream()
                .map(SelectedCategoryMapper::toDomain).collect(Collectors.toCollection(ArrayList::new));
    }

    private static List<SelectedCategoryDocument> toSelectedCategoryDocument(
            List<SelectedCategory> domains) {

        if (domains == null) return List.of();

        return domains.stream()
                .map(SelectedCategoryMapper::toDocument)
                .collect(Collectors.toList());
    }
}
