package com.todak.bbeaulife.application.member.exception;

public class DuplicateEmailException extends RuntimeException {

    private final String email;

    public DuplicateEmailException(String email) {
        this.email = email;
    }
}
