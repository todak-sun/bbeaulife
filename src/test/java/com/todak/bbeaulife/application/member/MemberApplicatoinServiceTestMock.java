package com.todak.bbeaulife.application.member;

import com.todak.bbeaulife.application.member.exception.NotFoundMemberException;
import com.todak.bbeaulife.application.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@Slf4j
@ExtendWith(MockitoExtension.class)
class MemberApplicatoinServiceTestMock {

    MemberApplicatoinService memberApplicatoinService;

    @Mock
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        this.memberApplicatoinService = new MemberApplicatoinService(memberRepository);
    }

    @DisplayName("mocking이 잘 됐는지 테스트")
    @Test
    void mock_exist() {
        assertNotNull(memberApplicatoinService);
    }

    @DisplayName("member를 정상 반환하는 테스트")
    @Test
    void get_member_by_id_success() {
        // given
        Long memberId = 1L;
        Member member = Mockito.mock(Member.class);

        given(memberRepository.findMemberById(memberId))
                .willReturn(Optional.of(member));

        // when
        Member foundedMember = memberApplicatoinService.getMemberById(memberId);

        // then
        assertEquals(member, foundedMember);
    }

    @DisplayName("member를 못찾을 경우 에러를 반환하는 테스트")
    @Test
    void get_member_by_id_fail_when_not_exist_member() {
        // given
        Long memberId = 1L;

        given(memberRepository.findMemberById(memberId))
                .willReturn(Optional.empty());

        // when & then
        NotFoundMemberException notFoundMemberException = assertThrows(NotFoundMemberException.class,
                () -> memberApplicatoinService.getMemberById(memberId),
                "member가 없는 경우 에러 반환"
        );

        assertEquals(memberId, notFoundMemberException.getNotFoundMemberId(),
                "못 찾은 member의 id를 가지고 있다.");
    }


}