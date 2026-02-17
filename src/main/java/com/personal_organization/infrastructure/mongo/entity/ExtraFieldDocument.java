package com.personal_organization.infrastructure.mongo.entity;


import java.util.List;

public class ExtraFieldDocument {

    private String id;
    private String label;
    private String type;
    private List<CategoryOptionDocument> options;

    public ExtraFieldDocument() {}

    public ExtraFieldDocument(String id, String label, String type, List<CategoryOptionDocument> options) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CategoryOptionDocument> getOptions() {
        return options;
    }

    public void setOptions(List<CategoryOptionDocument> options) {
        this.options = options;
    }
}
