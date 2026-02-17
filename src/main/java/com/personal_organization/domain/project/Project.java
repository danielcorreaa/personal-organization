package com.personal_organization.domain.project;

import com.personal_organization.domain.exception.BusinessException;
import com.personal_organization.domain.valueobject.Money;
import com.personal_organization.domain.valueobject.ProjectId;
import com.personal_organization.domain.valueobject.UserId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private final ProjectId id;
    private final UserId userId;
    private final String type;
    private final LocalDate date;
    private final String title;
    private final Money budget;
    private ProjectStatus status;
    private List<SelectedCategory> selectedCategories;
    private final List<ProjectItem> items = new ArrayList<>();

    public Project(
            ProjectId id,
            UserId userId,
            String type,
            String title,
            LocalDate date,
            Money budget, ProjectStatus status,
            List<SelectedCategory> selectedCategories1) {

        this.date = date;
        this.selectedCategories = selectedCategories1;
        if (budget == null || budget.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("O budget do projeto deve ser maior que zero");
        }

        this.id = id;
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.budget = budget;
        this.status = status;
    }

    public void addItem(ProjectItem item) {
        items.add(item);
    }

    public Money calculateTotalSpent() {
        return items.stream()
                .map(ProjectItem::getValue)
                .reduce(new Money(BigDecimal.ZERO), Money::add);
    }

    public boolean isOverBudget() {
        return calculateTotalSpent().getAmount()
                .compareTo(budget.getAmount()) > 0;
    }

    public void changeStatus(String statusChanged){
        status = ProjectStatus.valueOf(statusChanged);
    }

    public List<ProjectItem> getItems() {
        return items;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public Money getBudget() {
        return budget;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public UserId getUserId() {
        return userId;
    }

    public ProjectId getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<SelectedCategory> getSelectedCategories() {
        return selectedCategories;
    }
}