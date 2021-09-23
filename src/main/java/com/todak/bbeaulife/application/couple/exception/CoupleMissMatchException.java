package com.todak.bbeaulife.application.couple.exception;

import lombok.Getter;

public class CoupleMissMatchException extends RuntimeException {

    @Getter
    private final Long requesterId;
    @Getter
    private final Long requesteeId;

    public CoupleMissMatchException(Long requesterId, Long requesteeId) {
        this(requesterId, requesterId, "커플이 될 수 없습니다.");
    }

    public CoupleMissMatchException(Long requesterId, Long requesteeId, String message) {
        super(message);
        this.requesterId = requesterId;
        this.requesteeId = requesteeId;
    }

}
