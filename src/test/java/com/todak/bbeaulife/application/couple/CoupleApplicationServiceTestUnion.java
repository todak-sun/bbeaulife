package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.couple.repository.CoupleRepository;
import com.todak.bbeaulife.application.member.Member;
import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import com.todak.bbeaulife.application.member.UncertificatedMember;
import com.todak.bbeaulife.application.member.repository.MemberRepository;
import com.todak.bbeaulife.application.member.repository.UncertificatedMemberRepository;
import com.todak.bbeaulife.config.WithContainer;
import com.todak.bbeaulife.type.CoupleRole;
import com.todak.bbeaulife.type.Sex;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class CoupleApplicationServiceTestUnion extends WithContainer {

    @Autowired
    CoupleApplicationService coupleApplicationService;
    @Autowired
    MemberApplicatoinService memberApplicatoinService;

    @Autowired
    CoupleRepository coupleRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UncertificatedMemberRepository uncertificatedMemberRepository;

    @AfterEach
    void afterEach() {
        coupleRepository.deleteAll();
        memberRepository.deleteAll();
    }


    @Test
    void success_and_accept() {
        Member wife = createWife();
        Member husband = createHusband();

        Long timeOut = coupleApplicationService.suggestRelation(wife.getId(), CoupleRole.WIFE, husband.getId());
        Couple couple = coupleApplicationService.acceptRelation(husband.getId(), wife.getId());

        assertNotNull(timeOut);
        assertNotNull(couple);

        assertNotNull(couple.getId());
        assertNotNull(couple.getWife());

        assertEquals(CoupleRole.WIFE, couple.getWife().getRole());
        assertEquals(wife.getId(), couple.getWife().getId());
        assertEquals(wife.getEmail(), couple.getWife().getEmail());


        assertNotNull(couple.getHusband());
        assertEquals(CoupleRole.HUSBAND, couple.getHusband().getRole());
        assertEquals(husband.getId(), couple.getHusband().getId());
        assertEquals(husband.getEmail(), couple.getHusband().getEmail());

    }

    @Test
    void fetch_couple_test() {
        // given
        Member husband = createHusband();
        Member wife = createWife();

        coupleApplicationService.suggestRelation(husband.getId(), CoupleRole.HUSBAND, wife.getId());
        Couple newCouple = coupleApplicationService.acceptRelation(wife.getId(), husband.getId());

        // when
        Couple fetchedCouple = coupleApplicationService.fetchCouple(husband.getId());

        // then
        assertEquals(newCouple.getId(), fetchedCouple.getId(), "커플의 id가 일치한다.");
        assertEquals(wife.getId(), fetchedCouple.getWife().getId(), "정확히 wife의 정보가 나온다.");
        assertEquals(husband.getId(), fetchedCouple.getHusband().getId(), "정확히 husband의 정보가 나온다.");
    }

    private Member createWife() {
        String email = "wife@email.com";
        memberApplicatoinService.createMember(email, "wife-password", "wife-first", "wife-last", Sex.FEMALE);
        UncertificatedMember cache = uncertificatedMemberRepository.findById(email).get();
        return memberApplicatoinService.certificateMember(email, cache.getCirtificateCode());
    }

    private Member createHusband() {
        String email = "husband@email.com";
        memberApplicatoinService.createMember(email, "husband-password", "husband-first", "husband-last", Sex.MALE);
        UncertificatedMember cache = uncertificatedMemberRepository.findById(email).get();
        return memberApplicatoinService.certificateMember(email, cache.getCirtificateCode());
    }
}
