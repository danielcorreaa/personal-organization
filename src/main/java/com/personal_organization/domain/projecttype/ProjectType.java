package com.personal_organization.domain.projecttype;

import java.util.List;

public class ProjectType {

    private final String id;
    private final String label;
    private final String subtitle;
    private final String icon;
    private final String colorClass;
    private final String titleLabel;
    private final Boolean active;
    private final List<ExtraField> extraFields;

    public ProjectType(
            String id,
            String label,
            String subtitle,
            String icon,
            String colorClass,
            String titleLabel,
            Boolean active,
            List<ExtraField> extraFields
    ) {
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

    public Boolean getActive() {
        return active;
    }

    public List<ExtraField> getExtraFields() {
        return extraFields;
    }

    public String getLabel() {
        return label;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getIcon() {
        return icon;
    }

    public String getColorClass() {
        return colorClass;
    }

    public String getTitleLabel() {
        return titleLabel;
    }
}

