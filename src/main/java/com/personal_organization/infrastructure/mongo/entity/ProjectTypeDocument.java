package com.personal_organization.infrastructure.mongo.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "project_types")
public class ProjectTypeDocument {

    @Id
    private String id;

    private String label;
    private String subtitle;
    private String icon;
    private String colorClass;
    private String titleLabel;
    private Boolean active;

    private List<ExtraFieldDocument> extraFields;

    public ProjectTypeDocument() {}

    public ProjectTypeDocument(String id, String label, String subtitle, String icon, String colorClass, String titleLabel, Boolean active, List<ExtraFieldDocument> extraFields) {
        this.id = id;
        this.label = label;
        this.subtitle = subtitle;
        this.icon = icon;
        this.colorClass = colorClass;
        this.titleLabel = titleLabel;
        this.active = active;
        this.extraFields = extraFields;
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColorClass() {
        return colorClass;
    }

    public void setColorClass(String colorClass) {
        this.colorClass = colorClass;
    }

    public String getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(String titleLabel) {
        this.titleLabel = titleLabel;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ExtraFieldDocument> getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(List<ExtraFieldDocument> extraFields) {
        this.extraFields = extraFields;
    }
}

