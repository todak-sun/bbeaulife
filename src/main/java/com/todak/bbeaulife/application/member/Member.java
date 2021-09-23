package com.todak.bbeaulife.application.member;

import com.querydsl.core.annotations.QueryProjection;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.Getter;

import static com.todak.bbeaulife.type.CoupleRole.EMPTY;

public class Member {

    @Getter
    private final Long id;
    @Getter
    private final String email;
    private final CoupleRole role;

    @QueryProjection
    public Member(Long id, String email, CoupleRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public boolean hasPartner() {
        return !EMPTY.equals(role);
    }

}
