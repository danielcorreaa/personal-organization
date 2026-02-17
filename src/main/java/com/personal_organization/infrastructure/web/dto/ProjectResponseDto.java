package com.personal_organization.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ProjectResponseDto(
        String id,
        String userId,
        String type,
        String title,
        LocalDate date,
        String status,
        BigDecimal budget,
        List<SelectedCategoryDto> selectedCategories

) {}

