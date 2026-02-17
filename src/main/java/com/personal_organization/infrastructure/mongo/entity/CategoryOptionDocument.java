package com.personal_organization.infrastructure.mongo.entity;


public class CategoryOptionDocument {

    private String id;
    private String label;

    public CategoryOptionDocument() {}

    public CategoryOptionDocument(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
