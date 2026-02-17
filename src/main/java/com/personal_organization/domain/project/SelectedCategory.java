package com.personal_organization.domain.project;

public class SelectedCategory{

    private String id;
    private String value;

    public SelectedCategory(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
