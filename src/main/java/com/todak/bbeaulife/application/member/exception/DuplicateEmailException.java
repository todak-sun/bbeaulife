package com.todak.bbeaulife.application.member.exception;

import lombok.Getter;

public class DuplicateEmailException extends RuntimeException {

    @Getter
    private final String email;

    public DuplicateEmailException(String email) {
        this.email = email;
    }
}
