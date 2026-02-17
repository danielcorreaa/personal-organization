package com.personal_organization.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjectCreateDto(
        String type,        // ex: "mudanca"
        String title,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        BigDecimal budget,
        String status
) {}
