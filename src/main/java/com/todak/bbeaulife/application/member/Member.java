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

    private final FullName name;

    @Getter
    private final CoupleRole role;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(email, member.email) && role == member.role && Objects.equals(name, member.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, role, name);
    }
}
