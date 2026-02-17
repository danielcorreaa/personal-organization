package com.personal_organization.infrastructure.web.dto;

import java.math.BigDecimal;

public record ProjectItemCreateDto(
        String name,
        BigDecimal value,
        String category,
        String description
) {}

