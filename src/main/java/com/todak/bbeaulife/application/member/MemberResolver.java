package com.todak.bbeaulife.application.member;

import com.todak.bbeaulife.entities.MemberEntity;
import com.todak.bbeaulife.type.CoupleRole;
import com.todak.bbeaulife.type.FullName;

public final class MemberResolver extends Member {

    private MemberResolver(Long id, String email, FullName name, CoupleRole role) {
        super(id, email, name, role);
    }

    private MemberResolver(MemberEntity entity) {
        this(entity.getId(),
                entity.getEmail(),
                entity.getName(),
                entity.getRole());
    }

    public static MemberResolver resolve(MemberEntity entity) {
        return new MemberResolver(entity);
    }


}
