package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.member.Member;
import com.todak.bbeaulife.type.CoupleRole;
import com.todak.bbeaulife.type.FullName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoupleTest {


    @DisplayName("정상적으로 생성하는 테스트")
    @Test
    void create_success() {

        //given
        Long coupleId = 1L;

        Member wife = new Member(
                1L,
                "email@email.com",
                FullName.called("firstName1", "lastName1"),
                CoupleRole.WIFE);

        Member husband = new Member(
                2L,
                "email@email.com",
                FullName.called("firstName1", "lastName1"),
                CoupleRole.HUSBAND);
        //when
        Couple couple = Couple.create(coupleId, wife, husband);

        //then
        assertNotNull(couple, "정상적으로 생성된다.");
        assertEquals(coupleId, couple.getId());
        assertEquals(wife, couple.getWife(), "입력된 아내가 그대로 나온다.");
        assertEquals(husband, couple.getHusband(), "입력된 남편이 그대로 나온다.");
    }

    @Test
    @DisplayName("member가 둘 중 하나라도 파트너가 없다면, 에러를 반환")
    void create_fail_when_no_partner() {
        //given
        Long coupleId = 1L;

        Member memberHavingRole = new Member(
                1L,
                "email@email.com",
                FullName.called("firstName1", "lastName1"),
                CoupleRole.WIFE);

        Member memberHavingNoRole = new Member(
                2L,
                "email@email.com",
                FullName.called("firstName1", "lastName1"),
                null);

        // when & then
        assertThrows(RuntimeException.class,
                () -> Couple.create(coupleId, memberHavingRole, memberHavingNoRole),
                "에러를 반환한다.");

        assertThrows(RuntimeException.class,
                () -> Couple.create(coupleId, memberHavingNoRole, memberHavingNoRole),
                "에러를 반환한다.");
    }

    @Test
    @DisplayName("둘 다 같은 역할이라면 에러 반환")
    void create_fail_when_both_have_same_role() {
        //given
        Long coupleId = 1L;

        Member wifeA = new Member(
                1L,
                "email@email.com",
                FullName.called("firstName1", "lastName1"),
                CoupleRole.WIFE);

        Member wifeB = new Member(
                2L,
                "email@email.com",
                FullName.called("firstName1", "lastName1"),
                CoupleRole.WIFE);

        // when & then
        assertThrows(RuntimeException.class,
                () -> Couple.create(coupleId, wifeA, wifeB),
                "에러를 반환한다.");

        //given
        Member husbandA = new Member(
                1L,
                "email@email.com",
                FullName.called("firstName1", "lastName1"),
                CoupleRole.WIFE);

        Member husbandB = new Member(
                2L,
                "email@email.com",
                FullName.called("firstName1", "lastName1"),
                CoupleRole.WIFE);

        // when & then
        assertThrows(RuntimeException.class,
                () -> Couple.create(coupleId, husbandA, husbandB),
                "에러를 반환한다.");
    }

}