package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.couple.exception.CoupleMissMatchException;
import com.todak.bbeaulife.application.couple.repository.CoupleRepository;
import com.todak.bbeaulife.application.couple.repository.CoupleRequestRedisRepository;
import com.todak.bbeaulife.application.member.Member;
import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import com.todak.bbeaulife.application.member.exception.NotFoundMemberException;
import com.todak.bbeaulife.entities.CoupleEntity;
import com.todak.bbeaulife.type.CoupleRole;
import com.todak.bbeaulife.type.FullName;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CoupleApplicationServiceTestMock {

    CoupleApplicationService coupleApplicationService;

    @Mock
    MemberApplicatoinService memberApplicatoinService;

    @Mock
    CoupleRequestRedisRepository coupleRequestRedisRepository;

    @Mock
    CoupleRepository coupleRepository;

    @BeforeEach
    void setUp() {
        coupleApplicationService = new CoupleApplicationService(coupleRepository, coupleRequestRedisRepository, memberApplicatoinService);
    }

    @DisplayName("Mocking으로 인스턴스 생성 테스트")
    @Test
    void exist() {
        assertNotNull(coupleApplicationService, "mocking을 통해 인스턴스가 생성되었다.");
    }


    @DisplayName("커플을 가져오는 테스트")
    @Test
    void fetch_couple_success() {
        //given
        Long memberId = 1L;
        Long coupleId = 1L;

        Member wife = new Member(memberId, "wife@wife.com", FullName.called("wife", "wife"), CoupleRole.WIFE, coupleId);
        Member husband = new Member(memberId, "husband@husband.com", FullName.called("husband", "husband"), CoupleRole.HUSBAND, coupleId);

        CoupleEntity coupleEntity = mock(CoupleEntity.class);

        given(coupleEntity.getId())
                .willReturn(coupleId);
        given(memberApplicatoinService.getMemberById(memberId))
                .willReturn(wife);
        given(coupleRepository.findById(coupleId))
                .willReturn(Optional.of(coupleEntity));
        given(memberApplicatoinService.getPartner(memberId, coupleId))
                .willReturn(husband);

        Couple couple = coupleApplicationService.fetchCouple(memberId);

        assertNotNull(couple, "couple은 null이 아니다.");
        assertEquals(husband.getId(), couple.getHusband().getCoupleId(), "남편 필드에 정확히 남편이 꽂힌다");
        assertEquals(wife.getId(), couple.getWife().getId(), "아내 필드에 정확히 아내가 꽂힌다");
    }


    @DisplayName("커플이 아닌데 커플을 조회하려고 들면 에러가 난다.")
    @Test
    void fetch_couple_fail_when_not_has_couple() {
        //given
        Long memberId = 1L;
        Member solo = new Member(memberId, "wife@wife.com", FullName.called("wife", "wife"), CoupleRole.EMPTY, null);
        given(memberApplicatoinService.getMemberById(memberId))
                .willReturn(solo);

        // when & then
        assertThrows(RuntimeException.class,
                () -> coupleApplicationService.fetchCouple(memberId),
                "커플이 아닌데, 커플을 조회하려고하면 에러가 난다.");
    }


    @DisplayName("커플제안을 정상적으로 제안하는 테스트")
    @Test
    void suggest_relation_success() {
        //given
        Long requesterId = 1L;
        CoupleRole requesterRole = CoupleRole.HUSBAND;
        Long requesteeId = 2L;
        Member requester = Mockito.mock(Member.class);
        Member requestee = Mockito.mock(Member.class);
        CoupleRequestHash coupleRequestHash = CoupleRequestHash.create(requesterId, requesteeId, requesterRole);

        given(requester.getId()).willReturn(requesterId);
        given(requestee.getId()).willReturn(requesteeId);
        given(requester.hasPartner()).willReturn(false);
        given(requestee.hasPartner()).willReturn(false);

        given(coupleRequestRedisRepository.existsById(requesterId)).willReturn(false);
        given(coupleRequestRedisRepository.existsById(requesteeId)).willReturn(false);
        given(coupleRequestRedisRepository.save(coupleRequestHash)).willReturn(coupleRequestHash);

        given(memberApplicatoinService.getMemberById(requesterId)).willReturn(requester);
        given(memberApplicatoinService.getMemberById(requesteeId)).willReturn(requestee);

        // when
        Long timeOut = coupleApplicationService.suggestRelation(requesterId, requesterRole, requesteeId);

        // then
        assertEquals(Duration.ofMinutes(30L).getSeconds(), timeOut);
    }

    @DisplayName("신청자가 이미 커플을 신청했을 경우 실패")
    @Test
    void suggest_fail_when_requester_already_request() {
        //given
        Long requesterId = 1L;
        long requesteeId = 2L;

        given(coupleRequestRedisRepository.existsById(requesterId)).willReturn(true);

        // when & then
        CoupleMissMatchException coupleMissMatchException = assertThrows(CoupleMissMatchException.class,
                () -> coupleApplicationService.suggestRelation(requesterId, CoupleRole.HUSBAND, requesteeId),
                "이미 진행중인 커플 신청이 있을 경우, 에러를 반환한다.");

        assertEquals(requesterId, coupleMissMatchException.getRequesterId(), "신청자로 내가 뜬다.");
        assertEquals(requesteeId, coupleMissMatchException.getRequesteeId(), "피신청자로 상대방이 뜬다.");
        assertEquals("이미 진행중인 커플 신청이 있습니다.", coupleMissMatchException.getMessage(), "에러메시지가 정확히 일치한다.");
    }

    @DisplayName("피신청자가 이미 커플을 신청했을 경우 실패")
    @Test
    void suggest_fail_when_requestee_already_request() {
        //given
        Long requesterId = 1L;
        long requesteeId = 2L;

        given(coupleRequestRedisRepository.existsById(requesterId)).willReturn(false);
        given(coupleRequestRedisRepository.existsById(requesteeId)).willReturn(true);

        // when & then
        CoupleMissMatchException coupleMissMatchException = assertThrows(CoupleMissMatchException.class, () -> {
            coupleApplicationService.suggestRelation(requesterId, CoupleRole.HUSBAND, requesteeId);
        }, "상대방이 이미 커플 신청을 진행중이라면, 에러를 반환한다.");

        assertEquals(requesterId, coupleMissMatchException.getRequesterId(), "신청자로 내가 뜬다.");
        assertEquals(requesteeId, coupleMissMatchException.getRequesteeId(), "피신청자로 상대방이 뜬다.");
        assertEquals("상대방이 이미 커플 신청중에 있습니다.", coupleMissMatchException.getMessage(), "에러메시지가 정확히 일치한다.");
    }

    @DisplayName("신청자가 존재하지 않을 경우 실패")
    @Test
    void suggest_fail_when_requester_not_exist() {
        // given
        Long requesterId = 1L;
        CoupleRole requesterRole = CoupleRole.HUSBAND;
        Long requesteeId = 2L;

        given(coupleRequestRedisRepository.existsById(requesterId)).willReturn(false);
        given(coupleRequestRedisRepository.existsById(requesteeId)).willReturn(false);

        given(memberApplicatoinService.getMemberById(requesterId)).willThrow(NotFoundMemberException.class);

        // when & then
        assertThrows(NotFoundMemberException.class,
                () -> coupleApplicationService.suggestRelation(requesterId, requesterRole, requesteeId),
                "신청자가 존재하지 않을 경우, 에러 반환");
    }

    @DisplayName("피신청자가 존재하지 않을 경우 실패")
    @Test
    void suggest_fail_when_requestee_not_exsist() {
        // given
        Long requesterId = 1L;
        CoupleRole requesterRole = CoupleRole.HUSBAND;
        Long requesteeId = 2L;
        Member requester = Mockito.mock(Member.class);

        given(coupleRequestRedisRepository.existsById(requesterId)).willReturn(false);
        given(coupleRequestRedisRepository.existsById(requesteeId)).willReturn(false);

        given(memberApplicatoinService.getMemberById(requesterId)).willReturn(requester);
        given(memberApplicatoinService.getMemberById(requesteeId)).willThrow(NotFoundMemberException.class);

        // when & then
        assertThrows(NotFoundMemberException.class,
                () -> coupleApplicationService.suggestRelation(requesterId, requesterRole, requesteeId),
                "피신청자가 존재하지 않을 경우, 에러 반환");
    }

    @DisplayName("신청자가 커플인데 또 신청한 경우 실패")
    @Test
    void suggest_fail_when_requester_already_have_relationship() {
        // given
        Long requesterId = 1L;
        CoupleRole requesterRole = CoupleRole.HUSBAND;
        Long requesteeId = 2L;
        Member requester = Mockito.mock(Member.class);

        given(requester.hasPartner()).willReturn(true);

        given(coupleRequestRedisRepository.existsById(requesterId)).willReturn(false);
        given(coupleRequestRedisRepository.existsById(requesteeId)).willReturn(false);

        given(memberApplicatoinService.getMemberById(requesterId)).willReturn(requester);

        // when & then
        CoupleMissMatchException coupleMissMatchException = assertThrows(CoupleMissMatchException.class,
                () -> coupleApplicationService.suggestRelation(requesterId, requesterRole, requesteeId),
                "신청자가 커플인데 또 신청하면 에러를 반환한다."
        );

        assertEquals(requesterId, coupleMissMatchException.getRequesterId(), "신청자로 내가 뜬다.");
        assertEquals(requesteeId, coupleMissMatchException.getRequesteeId(), "피신청자로 상대방이 뜬다");
        assertEquals("이미 커플입니다.", coupleMissMatchException.getMessage(), "메시지가 정확히 일치한다.");
    }

    @DisplayName("피신청자가 커플인데 또 신청한 경우 실패")
    @Test
    void suggest_fail_when_requestee_already_have_relationship() {
        // given
        Long requesterId = 1L;
        CoupleRole requesterRole = CoupleRole.HUSBAND;
        Long requesteeId = 2L;
        Member requester = Mockito.mock(Member.class);
        Member requestee = Mockito.mock(Member.class);

        given(requester.hasPartner()).willReturn(false);
        given(requestee.hasPartner()).willReturn(true);

        given(coupleRequestRedisRepository.existsById(requesterId)).willReturn(false);
        given(coupleRequestRedisRepository.existsById(requesteeId)).willReturn(false);

        given(memberApplicatoinService.getMemberById(requesterId)).willReturn(requester);
        given(memberApplicatoinService.getMemberById(requesteeId)).willReturn(requestee);

        // when & then
        CoupleMissMatchException coupleMissMatchException = assertThrows(CoupleMissMatchException.class,
                () -> coupleApplicationService.suggestRelation(requesterId, requesterRole, requesteeId),
                "피신청자가 커플인데 신청하면 에러를 반환한다."
        );

        assertEquals(requesterId, coupleMissMatchException.getRequesterId(), "신청자로 내가 뜬다.");
        assertEquals(requesteeId, coupleMissMatchException.getRequesteeId(), "피신청자로 상대방이 뜬다");
        assertEquals("상대방이 커플입니다.", coupleMissMatchException.getMessage(), "메시지가 정확히 일치한다.");
    }

    @DisplayName("수락 성공 테스트")
    @Test
    void accept_success() {
        //given
        Long requesteeId = 1L;
        Long requesterId = 2L;
        Long coupleId = 1L;
        CoupleRole requesterRole = CoupleRole.HUSBAND;
        CoupleRequestHash coupleRequestHash = CoupleRequestHash.create(requesterId, requesteeId, requesterRole);

        CoupleEntity couple = Mockito.mock(CoupleEntity.class);
        given(couple.getId()).willReturn(coupleId);

        Member requester = Mockito.mock(Member.class);
        given(requester.hasPartner()).willReturn(true);
        given(requester.getRole()).willReturn(requesterRole);

        Member requestee = Mockito.mock(Member.class);
        given(requestee.hasPartner()).willReturn(true);
        given(requestee.getRole()).willReturn(requesterRole.getOpposite());

        given(coupleRequestRedisRepository.findById(requesterId)).willReturn(Optional.of(coupleRequestHash));

        given(coupleRepository.save(any())).willReturn(couple);

        given(memberApplicatoinService.promoteAsCouple(coupleRequestHash.getRequesteeId(), coupleId, coupleRequestHash.getRequesteeRole()))
                .willReturn(requestee);

        given(memberApplicatoinService.promoteAsCouple(coupleRequestHash.getRequesterId(), coupleId, coupleRequestHash.getRequesterRole()))
                .willReturn(requester);

        // when
        Couple result = coupleApplicationService.acceptRelation(requesteeId, requesterId);

        // then
        assertNotNull(result);
    }

    @DisplayName("커플을 수락하려고 했는데, 신청 데이터가 존재하지 않으면 실패")
    @Test
    void accept_relation_fail_when_not_exsist_request() {
        //given
        long requesteeId = 1L;
        long requesterId = 2L;

        given(coupleRequestRedisRepository.findById(requesterId))
                .willReturn(Optional.empty());

        //when & then
        CoupleMissMatchException coupleMissMatchException = assertThrows(CoupleMissMatchException.class,
                () -> coupleApplicationService.acceptRelation(requesteeId, requesterId),
                "신청목록에 신청자가 존재하지 않는다면 에러가 발생하지 않는다.");

        assertEquals(requesterId, coupleMissMatchException.getRequesterId(), "신청자의 ID가 에러객체 내에 동일하게 꽂힌다.");
        assertEquals(requesteeId, coupleMissMatchException.getRequesteeId(), "피신청자의 ID가 에러객체 내에 동일하게 꽂힌다.");
    }

    @DisplayName("커플을 수락하려고 했는데, 신청자가 신청한 사람이 내가 아니면 에러")
    @Test
    void accept_relation_fail_when_not_match_requestee() {
        //given
        long requesteeId = 1L;
        long requesterId = 2L;
        CoupleRequestHash coupleRequestHash = CoupleRequestHash.create(requesterId, 3L, CoupleRole.HUSBAND);

        given(coupleRequestRedisRepository.findById(requesterId))
                .willReturn(Optional.of(coupleRequestHash));

        //when & then
        assertThrows(RuntimeException.class,
                () -> coupleApplicationService.acceptRelation(requesteeId, requesterId),
                "신청자가 신청한 사람이 피신청자가 아닌데 수락하려고 하면 에러가난다.");
    }

}