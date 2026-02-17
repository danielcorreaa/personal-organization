package com.personal_organization.infrastructure.mongo.entity;

import com.personal_organization.domain.project.ProjectStatus;
import com.personal_organization.domain.project.SelectedCategory;
import com.personal_organization.domain.valueobject.Money;
import com.personal_organization.domain.valueobject.ProjectId;
import com.personal_organization.domain.valueobject.UserId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "projects")
public class ProjectDocument {

    @Id
    private String id;
    private String userId;
    private String type;
    private LocalDate date;
    private String title;
    private BigDecimal budget;
    private String status;
    private List<SelectedCategoryDocument> selectedCategories = new ArrayList<>();

    public ProjectDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectedCategoryDocument> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<SelectedCategoryDocument> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }
}

