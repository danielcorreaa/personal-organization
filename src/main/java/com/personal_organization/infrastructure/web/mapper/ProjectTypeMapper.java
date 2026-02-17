package com.personal_organization.infrastructure.web.mapper;

import com.personal_organization.domain.projecttype.CategoryOption;
import com.personal_organization.domain.projecttype.ExtraField;
import com.personal_organization.domain.projecttype.ProjectType;
import com.personal_organization.infrastructure.web.dto.CategoryOptionDto;
import com.personal_organization.infrastructure.web.dto.ExtraFieldDto;
import com.personal_organization.infrastructure.web.dto.ProjectTypeDto;

import java.util.List;
import java.util.stream.Collectors;

public final class ProjectTypeMapper {

    private ProjectTypeMapper() {}


    public static ProjectType toDomain(ProjectTypeDto dto) {
        return new ProjectType(
                dto.id(),
                dto.label(),
                dto.subtitle(),
                dto.icon(),
                dto.colorClass(),
                dto.titleLabel(),
                dto.active(),
                mapExtraFieldsToDomain(dto.extraFields())
        );
    }

    private static List<ExtraField> mapExtraFieldsToDomain(List<ExtraFieldDto> dtos) {
        if (dtos == null) return List.of();

        return dtos.stream()
                .map(ProjectTypeMapper::toDomain)
                .collect(Collectors.toList());
    }

    private static ExtraField toDomain(ExtraFieldDto dto) {
        return new ExtraField(
                dto.id(),
                dto.label(),
                dto.type(),
                mapOptionsToDomain(dto.options())
        );
    }

    private static List<CategoryOption> mapOptionsToDomain(List<CategoryOptionDto> dtos) {
        if (dtos == null) return List.of();

        return dtos.stream()
                .map(ProjectTypeMapper::toDomain)
                .collect(Collectors.toList());
    }

    private static CategoryOption toDomain(CategoryOptionDto dto) {
        return new CategoryOption(
                dto.id(),
                dto.label()
        );
    }


    public static ProjectTypeDto toDto(ProjectType domain) {

        return new ProjectTypeDto(
                domain.getId(),
                domain.getLabel(),
                domain.getSubtitle(),
                domain.getIcon(),
                domain.getColorClass(),
                domain.getTitleLabel(),
                domain.getActive(),
                mapExtraFieldsToDto(domain.getExtraFields())
        );
    }

    private static List<ExtraFieldDto> mapExtraFieldsToDto(List<ExtraField> fields) {
        if (fields == null) return List.of();

        return fields.stream()
                .map(ProjectTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    private static ExtraFieldDto toDto(ExtraField field) {
        return new ExtraFieldDto(
                field.getId(),
                field.getLabel(),
                field.getType(),
                mapOptionsToDto(field.getOptions())
        );
    }

    private static List<CategoryOptionDto> mapOptionsToDto(List<CategoryOption> options) {
        if (options == null) return List.of();

        return options.stream()
                .map(ProjectTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    private static CategoryOptionDto toDto(CategoryOption option) {
        return new CategoryOptionDto(
                option.getId(),
                option.getLabel()
        );
    }
}
