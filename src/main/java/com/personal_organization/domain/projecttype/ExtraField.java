package com.personal_organization.domain.projecttype;

import java.util.List;

public class ExtraField {

    private final String id;
    private final String label;
    private final String type;
    private final List<CategoryOption> options;

    public ExtraField(
            String id,
            String label,
            String type,
            List<CategoryOption> options
    ) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.options = options;
    }

    public List<CategoryOption> getOptions() {
        return options;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }
}
