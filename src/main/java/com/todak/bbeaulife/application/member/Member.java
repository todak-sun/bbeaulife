package com.todak.bbeaulife.application.member;

import com.querydsl.core.annotations.QueryProjection;
import com.todak.bbeaulife.type.CoupleRole;
import com.todak.bbeaulife.type.FullName;
import lombok.Getter;

import java.util.Objects;

import static com.todak.bbeaulife.type.CoupleRole.EMPTY;

public class Member {

    @Getter
    private final Long id;
    @Getter
    private final String email;
    @Getter
    private final CoupleRole role;

    private final FullName name;

    @QueryProjection
    public Member(Long id, String email, FullName name, CoupleRole role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = Objects.requireNonNullElse(role, EMPTY);
    }

    public boolean hasPartner() {
        return !EMPTY.equals(role);
    }

}
