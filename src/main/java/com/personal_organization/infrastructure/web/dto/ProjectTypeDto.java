package com.personal_organization.infrastructure.web.dto;

import java.util.List;

public record ProjectTypeDto(
        String id,
        String label,
        String subtitle,
        String icon,
        String colorClass,
        String titleLabel,
        boolean active,
        List<ExtraFieldDto> extraFields
) {}
