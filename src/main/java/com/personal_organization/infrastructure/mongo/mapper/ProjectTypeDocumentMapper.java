package com.personal_organization.infrastructure.mongo.mapper;

import com.personal_organization.domain.projecttype.CategoryOption;
import com.personal_organization.domain.projecttype.ExtraField;
import com.personal_organization.domain.projecttype.ProjectType;
import com.personal_organization.infrastructure.mongo.entity.CategoryOptionDocument;
import com.personal_organization.infrastructure.mongo.entity.ExtraFieldDocument;
import com.personal_organization.infrastructure.mongo.entity.ProjectTypeDocument;


import java.util.stream.Collectors;

public class ProjectTypeDocumentMapper {

    public static ProjectType toDomain(ProjectTypeDocument doc) {
        return new ProjectType(
                doc.getId(),
                doc.getLabel(),
                doc.getSubtitle(),
                doc.getIcon(),
                doc.getColorClass(),
                doc.getTitleLabel(),
                doc.getActive(),
                doc.getExtraFields().stream()
                        .map(ProjectTypeDocumentMapper::toDomain).toList()
        );
    }

    private static ExtraField toDomain(ExtraFieldDocument doc) {
        return new ExtraField(
                doc.getId(),
                doc.getLabel(),
                doc.getType(),
                doc.getOptions().stream()
                        .map(o -> new CategoryOption(o.getId(), o.getLabel()))
                        .collect(Collectors.toList())
        );
    }

    public static ProjectTypeDocument toDocument(ProjectType doc) {
        return new ProjectTypeDocument(
                doc.getId(),
                doc.getLabel(),
                doc.getSubtitle(),
                doc.getIcon(),
                doc.getColorClass(),
                doc.getTitleLabel(),
                doc.getActive(),
                doc.getExtraFields().stream()
                        .map(ProjectTypeDocumentMapper::toDocument).toList()
        );
    }

    private static ExtraFieldDocument toDocument(ExtraField doc) {
        return new ExtraFieldDocument(
                doc.getId(),
                doc.getLabel(),
                doc.getType(),
                doc.getOptions().stream()
                        .map(o -> new CategoryOptionDocument(o.getId(), o.getLabel())).toList()
        );
    }
}
