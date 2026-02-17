package com.personal_organization.infrastructure.web.mapper;

import com.personal_organization.domain.project.ProjectItem;
import com.personal_organization.domain.valueobject.*;
import com.personal_organization.infrastructure.web.dto.ProjectItemCreateDto;
import com.personal_organization.infrastructure.web.dto.ProjectItemResponseDto;

import java.util.UUID;

public class ProjectItemMapper {

    private ProjectItemMapper() {}

    public static ProjectItem toDomain(
            String projectId,
            UserId userId,
            ProjectItemCreateDto dto
    ) {
        return new ProjectItem(
                UUID.randomUUID().toString(),
                new ProjectId(projectId),
                userId,
                dto.name(),
                new Money(dto.value()),
                new CategoryId(dto.category()),
                dto.description()
        );
    }

    public static ProjectItemResponseDto toResponse(ProjectItem item) {
        return new ProjectItemResponseDto(
                item.getId(),
                item.getProjectId().value(),
                item.getUserId().value(),
                item.getName(),
                item.getValue().getAmount(),
                item.getCategory().value(),
                item.isCompleted(),
                item.getDescription()
        );
    }
}
