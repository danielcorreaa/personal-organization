package com.personal_organization.infrastructure.web.dto;

import java.util.List;

public record ExtraFieldDto(
        String id,
        String label,
        String type, // category
        List<CategoryOptionDto> options
) {}
