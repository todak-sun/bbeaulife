package com.todak.bbeaulife.application.member.exception;

import lombok.Getter;

public class NotFoundMemberException extends RuntimeException {

    @Getter
    private final Long notFoundMemberId;

    public NotFoundMemberException(Long notFoundMemberId) {
        this(notFoundMemberId, "존재하지 않는 사용자입니다.");
    }

    public NotFoundMemberException(Long notFoundMemberId, String message) {
        super(message);
        this.notFoundMemberId = notFoundMemberId;
    }
}
