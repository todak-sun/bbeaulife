package com.todak.bbeaulife.application.member;

import com.todak.bbeaulife.type.CoupleRole;
import com.todak.bbeaulife.type.FullName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void create_success() {
        //given
        Long memberId = 1L;
        String email = "email@email.com";
        CoupleRole role = CoupleRole.HUSBAND;
        Long coupleId = 1L;
        FullName name = FullName.called("first", "last");

        //when
        Member member = new Member(memberId, email, name, role, coupleId);

        //then
        assertNotNull(member, "null이 아니다.");
        assertEquals(memberId, member.getId());
        assertEquals(email, member.getEmail());
        assertEquals(role, member.getRole());
        assertEquals(coupleId, member.getCoupleId());
    }

    @Test
    void equlas_test() {
        //given
        Member memberA = new Member(1L, "email@email.com", FullName.called("first", "last"), CoupleRole.EMPTY, null);
        Member memberB = new Member(1L, "email@email.com", FullName.called("first", "last"), CoupleRole.EMPTY, null);

        //when & then
        assertTrue(memberA.equals(memberB), "데이터가 같다면, 같은 멤버로 판단한다.");
    }

    @Test
    void hashcode_test() {
        //given
        Member memberA = new Member(1L, "email@email.com", FullName.called("first", "last"), CoupleRole.EMPTY, null);
        Member memberB = new Member(1L, "email@email.com", FullName.called("first", "last"), CoupleRole.EMPTY, null);

        //when & then
        assertEquals(memberA.hashCode(), memberB.hashCode(), "데이터가 같다면, 해쉬코드도 같다.");
    }

}