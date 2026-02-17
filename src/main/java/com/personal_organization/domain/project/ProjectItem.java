package com.personal_organization.domain.project;


import com.personal_organization.domain.exception.BusinessException;
import com.personal_organization.domain.valueobject.CategoryId;
import com.personal_organization.domain.valueobject.Money;
import com.personal_organization.domain.valueobject.ProjectId;
import com.personal_organization.domain.valueobject.UserId;

import java.math.BigDecimal;

public class ProjectItem {

    private final String id;
    private final ProjectId projectId;
    private final UserId userId;

    private String name;
    private Money value;
    private CategoryId category;
    private boolean completed;
    private String description;

    public ProjectItem(
            String id,
            ProjectId projectId,
            UserId userId,
            String name,
            Money value,
            CategoryId category,
            String description
    ) {
        if (name == null || name.isBlank()) {
            throw new BusinessException("Nome do item é obrigatório");
        }

        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.name = name;
        this.value = value;
        this.category = category;
        this.description = description;
        this.completed = false;
    }


    public Money getValue() {
        return value;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markAsCompleted() {
        this.completed = true;
    }
    public void markAsNoCompleted() {
        this.completed = false;
    }


    public String getId() {
        return id;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public CategoryId getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public void changeValue(BigDecimal value) {
        this.value = new Money(value);
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}