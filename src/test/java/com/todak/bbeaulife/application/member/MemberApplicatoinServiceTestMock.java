package com.todak.bbeaulife.application.member;

import com.todak.bbeaulife.application.member.exception.NotFoundMemberException;
import com.todak.bbeaulife.application.member.repository.MemberRepository;
import com.todak.bbeaulife.entities.MemberEntity;
import com.todak.bbeaulife.type.FullName;
import com.todak.bbeaulife.type.Sex;
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
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.spy;

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


    @DisplayName("DB에서 조회한 결과가 있다면 true, 아니라면 false 리턴")
    @Test
    void exsist_by_id_test() {
        //given
        long exsistMemberId = 1L;
        long notExistMemberId = 2L;

        given(memberRepository.existsByIdAndActivatedTrue(exsistMemberId))
                .willReturn(true);
        given(memberRepository.existsByIdAndActivatedTrue(notExistMemberId))
                .willReturn(false);

        //when & then
        assertTrue(memberApplicatoinService.exsistById(exsistMemberId));
        assertFalse(memberApplicatoinService.exsistById(notExistMemberId));
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

    @DisplayName("사용자가 존재한다면 탈퇴가 가능하다.")
    @Test
    void withdraw_success() {
        //given
        long memberId = 1L;
        MemberEntity memberEntity = spy(MemberEntity.create("email@email.com", "password", FullName.called("name", "name"), Sex.MALE));

        given(memberRepository.findByIdAndActivatedTrue(memberId))
                .willReturn(Optional.of(memberEntity));

        // when
        memberApplicatoinService.withdraw(memberId);

        // then
        then(memberEntity)
                .should()
                .deactivate();
    }

    @DisplayName("사용자가 존재하지 않는다면 에러가 난다.")
    @Test
    void withdraw_fail_not_exsist_member() {
        //given
        long memberId = 1L;

        given(memberRepository.findByIdAndActivatedTrue(memberId))
                .willReturn(Optional.empty());

        // when & then
        NotFoundMemberException notFoundMemberException = assertThrows(NotFoundMemberException.class,
                () -> memberApplicatoinService.withdraw(memberId),
                "존재하지 않는 멤버를 탈퇴처리하려면 에러가 난다");

        assertEquals(memberId, notFoundMemberException.getNotFoundMemberId(), "찾지 못한 사용자의 ID가 에러에 매핑된다.");
    }


}