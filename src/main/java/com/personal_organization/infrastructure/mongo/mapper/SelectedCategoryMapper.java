package com.personal_organization.infrastructure.mongo.mapper;

import com.personal_organization.domain.project.SelectedCategory;
import com.personal_organization.infrastructure.mongo.entity.SelectedCategoryDocument;

public class SelectedCategoryMapper {

    public static SelectedCategory toDomain(SelectedCategoryDocument doc) {
        if (doc == null) return null;

        return new SelectedCategory(
                doc.getId(),
                doc.getValue()
        );
    }

    public static SelectedCategoryDocument toDocument(SelectedCategory domain) {
        if (domain == null) return null;

        SelectedCategoryDocument doc = new SelectedCategoryDocument();
        doc.setId(domain.getId());
        doc.setValue(domain.getValue());
        return doc;
    }
}
