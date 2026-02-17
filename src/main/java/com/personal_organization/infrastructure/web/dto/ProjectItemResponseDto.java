package com.personal_organization.infrastructure.web.dto;

import java.math.BigDecimal;

public record ProjectItemResponseDto(
        String id,
        String projectId,
        String userId,
        String name,
        BigDecimal value,
        String category,
        boolean completed,
        String description
) {}
