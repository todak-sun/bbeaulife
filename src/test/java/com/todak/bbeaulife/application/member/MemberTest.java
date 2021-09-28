package com.todak.bbeaulife.application.member;

import com.todak.bbeaulife.type.CoupleRole;
import com.todak.bbeaulife.type.FullName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MemberTest {

    @Test
    public void create_success() {
        Long memberId = 1L;
        String email = "email@email.com";
        CoupleRole role = CoupleRole.HUSBAND;
        Long coupleId = 1L;
        FullName name = FullName.called("first", "last");


        Member member = new Member(memberId, email, name, role, coupleId);

        assertNotNull(member, "null이 아니다.");
        assertEquals(memberId, member.getId());
        assertEquals(email, member.getEmail());
        assertEquals(role, member.getRole());
        assertEquals(coupleId, member.getCoupleId());
    }

}