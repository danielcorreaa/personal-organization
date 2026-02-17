package com.personal_organization.domain.valueobject;

import com.personal_organization.domain.exception.BusinessException;

public record UserId(String value) {
    public UserId {
        if (value == null || value.isBlank()) {
            throw new BusinessException("UserId inválido");
        }
    }

}