package com.personal_organization.infrastructure.mongo.entity;

public class SelectedCategoryDocument {

    private String id;
    private String value;

    public SelectedCategoryDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
