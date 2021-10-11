package com.todak.bbeaulife.exception;

import com.todak.bbeaulife.api.response.ErrorDto;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class InvalidRequestException extends RuntimeException {

    @Getter
    private final List<ErrorDto> errors;

    public InvalidRequestException(List<FieldError> errors) {
        this.errors = errors.stream()
                .map(ErrorDto::new)
                .collect(Collectors.toList());
    }
}
