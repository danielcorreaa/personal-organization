package com.personal_organization.domain.valueobject;

import com.personal_organization.domain.exception.BusinessException;

public record CategoryId(String value) {
    public CategoryId {
        if (value == null || value.isBlank()) {
            throw new BusinessException("CategoryId inválido");
        }
    }
}