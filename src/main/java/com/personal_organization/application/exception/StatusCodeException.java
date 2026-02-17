package com.personal_organization.application.exception;

import org.springframework.http.HttpStatus;

public class StatusCodeException extends RuntimeException {

    private final HttpStatus status;

    public StatusCodeException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
