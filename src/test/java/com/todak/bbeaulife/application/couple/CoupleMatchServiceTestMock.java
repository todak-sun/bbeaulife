package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.couple.exception.CoupleMissMatchException;
import com.todak.bbeaulife.entities.MemberEntity;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CoupleMatchServiceTestMock {

    CoupleMatchService coupleMatchService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    CoupleRequestRedisRepository coupleRequestRedisRepository;

    @BeforeEach
    void setUp() {
        coupleMatchService = new CoupleMatchService(memberRepository, coupleRequestRedisRepository);
    }

    @DisplayName("Mocking으로 인스턴스 생성 테스트")
    @Test
    void exist() {
        assertNotNull(coupleMatchService, "mocking을 통해 인스턴스가 생성되었다.");
    }

    @DisplayName("커플제안을 정상적으로 제안하는 테스트")
    @Test
    void suggest_relation_success() {
        //given
        Long myId = 1L;
        CoupleRole myRole = CoupleRole.HUSBAND;
        Long partnerId = 2L;
        MemberEntity me = Mockito.mock(MemberEntity.class);
        MemberEntity partner = Mockito.mock(MemberEntity.class);
        CoupleRequestHash coupleRequestHash = CoupleRequestHash.create(myId, partnerId, myRole);

        given(me.getId()).willReturn(myId);
        given(partner.getId()).willReturn(partnerId);
        given(me.hasPartner()).willReturn(false);
        given(partner.hasPartner()).willReturn(false);

        given(coupleRequestRedisRepository.existsById(myId)).willReturn(false);
        given(coupleRequestRedisRepository.existsById(partnerId)).willReturn(false);
        given(coupleRequestRedisRepository.save(coupleRequestHash)).willReturn(coupleRequestHash);

        given(memberRepository.findById(myId)).willReturn(Optional.of(me));
        given(memberRepository.findById(partnerId)).willReturn(Optional.of(partner));

        // when
        Long timeOut = coupleMatchService.suggestRelation(myId, myRole, partnerId);

        // then
        assertEquals(Duration.ofMinutes(30L).getSeconds(), timeOut);
    }

    @DisplayName("신청자가 이미 커플을 신청했을 경우 실패")
    @Test
    void suggest_fail_when_requester_already_request() {
        //given
        Long myId = 1L;
        long partnerId = 2L;

        given(coupleRequestRedisRepository.existsById(myId)).willReturn(true);

        // when & then
        CoupleMissMatchException coupleMissMatchException = assertThrows(CoupleMissMatchException.class, () -> {
            coupleMatchService.suggestRelation(myId, CoupleRole.HUSBAND, partnerId);
        }, "이미 진행중인 커플 신청이 있을 경우, 에러를 반환한다.");

        assertEquals(myId, coupleMissMatchException.getRequesterId(), "신청자로 내가 뜬다.");
        assertEquals(partnerId, coupleMissMatchException.getRequesteeId(), "피신청자로 상대방이 뜬다.");
        assertEquals("이미 진행중인 커플 신청이 있습니다.", coupleMissMatchException.getMessage(), "에러메시지가 정확히 일치한다.");
    }

    @DisplayName("피신청자가 이미 커플을 신청했을 경우 실패")
    @Test
    void suggest_fail_when_requestee_already_request() {
        //given
        Long myId = 1L;
        long partnerId = 2L;

        given(coupleRequestRedisRepository.existsById(myId)).willReturn(false);
        given(coupleRequestRedisRepository.existsById(partnerId)).willReturn(true);

        // when & then
        CoupleMissMatchException coupleMissMatchException = assertThrows(CoupleMissMatchException.class, () -> {
            coupleMatchService.suggestRelation(myId, CoupleRole.HUSBAND, partnerId);
        }, "상대방이 이미 커플 신청을 진행중이라면, 에러를 반환한다.");

        assertEquals(myId, coupleMissMatchException.getRequesterId(), "신청자로 내가 뜬다.");
        assertEquals(partnerId, coupleMissMatchException.getRequesteeId(), "피신청자로 상대방이 뜬다.");
        assertEquals("상대방이 이미 커플 신청중에 있습니다.", coupleMissMatchException.getMessage(), "에러메시지가 정확히 일치한다.");
    }

    @DisplayName("신청자가 커플인데 또 신청한 경우")
    @Test
    void suggest_fail_when_requester_already_have_relationship() {
        //given
        Long myId = 1L;
        CoupleRole myRole = CoupleRole.HUSBAND;
        Long partnerId = 2L;
        MemberEntity me = Mockito.mock(MemberEntity.class);

        given(me.hasPartner()).willReturn(true);

        given(coupleRequestRedisRepository.existsById(myId)).willReturn(false);
        given(coupleRequestRedisRepository.existsById(partnerId)).willReturn(false);

        given(memberRepository.findById(myId)).willReturn(Optional.of(me));

        CoupleMissMatchException coupleMissMatchException = assertThrows(CoupleMissMatchException.class,
                () -> coupleMatchService.suggestRelation(myId, myRole, partnerId),
                "신청자가 커플인데 또 신청하면 에러를 반환한다."
        );

        assertEquals(myId, coupleMissMatchException.getRequesterId(), "신청자로 내가 뜬다.");
        assertEquals(partnerId, coupleMissMatchException.getRequesteeId(), "피신청자로 상대방이 뜬다");
        assertEquals("이미 커플입니다.", coupleMissMatchException.getMessage(), "메시지가 정확히 일치한다.");
    }


}