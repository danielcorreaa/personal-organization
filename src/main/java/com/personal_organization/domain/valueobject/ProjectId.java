package com.personal_organization.domain.valueobject;

import com.personal_organization.domain.exception.BusinessException;

public record ProjectId(String value) {
    public ProjectId {
        if (value == null || value.isBlank()) {
            throw new BusinessException("ProjectId inválido");
        }
    }
}