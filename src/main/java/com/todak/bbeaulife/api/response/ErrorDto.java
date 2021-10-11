package com.todak.bbeaulife.api.response;

import org.springframework.validation.FieldError;

public class ErrorDto {

    private final String property;
    private final Object rejectedValue;
    private final String message;

    public ErrorDto(FieldError error) {
        this.property = error.getField();
        this.rejectedValue = error.getRejectedValue();
        this.message = error.getDefaultMessage();
    }
}
