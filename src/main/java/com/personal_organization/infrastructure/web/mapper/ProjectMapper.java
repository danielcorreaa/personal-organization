package com.personal_organization.infrastructure.web.mapper;

import com.personal_organization.domain.project.Project;
import com.personal_organization.domain.project.ProjectStatus;
import com.personal_organization.domain.project.SelectedCategory;
import com.personal_organization.domain.valueobject.Money;
import com.personal_organization.domain.valueobject.ProjectId;
import com.personal_organization.domain.valueobject.UserId;
import com.personal_organization.infrastructure.web.dto.ProjectCreateDto;
import com.personal_organization.infrastructure.web.dto.ProjectResponseDto;
import com.personal_organization.infrastructure.web.dto.SelectedCategoryDto;

import java.util.ArrayList;
import java.util.UUID;

public class ProjectMapper {

    public static Project toDomain(ProjectCreateDto dto, String userId) {
        return new Project(
                new ProjectId(UUID.randomUUID().toString()),
                new UserId(userId),
                dto.type(),
                dto.title(),
                dto.date(),
                new Money(dto.budget()),
                ProjectStatus.valueOf(dto.status()),
                new ArrayList<>()
        );
    }


    public static Project toDomainToUpdate(
            String projectId,
            ProjectCreateDto dto,
            UserId userId
    ) {
        return new Project(
                new ProjectId(projectId),
                userId,
                dto.type(),
                dto.title(),
                dto.date(),
                new Money(dto.budget()),
                ProjectStatus.valueOf(dto.status()), new ArrayList<>()
        );
    }

    public static ProjectResponseDto toResponse(Project project) {
        return new ProjectResponseDto(
                project.getId().value(),
                project.getUserId().value(),
                project.getType(),
                project.getTitle(),
                project.getDate(),
                project.getStatus().toString(),
                project.getBudget().getAmount(),
                project.getSelectedCategories().stream().map(ProjectMapper::toSelectedCategoryDto).toList()
        );
    }
    public static SelectedCategoryDto toSelectedCategoryDto(SelectedCategory selectedCategory){
        return new SelectedCategoryDto(selectedCategory.getId(), selectedCategory.getValue());
    }

    public static SelectedCategory toSelectedCategory(SelectedCategoryDto selectedCategoryDto){
        return new SelectedCategory(selectedCategoryDto.id(), selectedCategoryDto.value());
    }
}
