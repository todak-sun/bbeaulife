package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.application.accountBook.entity.AccountBookEntity;
import com.todak.bbeaulife.application.accountBook.exception.AlreadyExistAccountBookException;
import com.todak.bbeaulife.application.accountBook.repository.AccountBookHistoryRepository;
import com.todak.bbeaulife.application.accountBook.repository.AccountBookIncomeHistoryRepository;
import com.todak.bbeaulife.application.accountBook.repository.AccountBookRepository;
import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AccountBookApplicationServiceTestMock {

    @Mock
    AccountBookRepository accountBookRepository;

    @Mock
    AccountBookHistoryRepository accountBookHistoryRepository;

    @Mock
    AccountBookIncomeHistoryRepository accountBookIncomeHistoryRepository;

    @Mock
    MemberApplicatoinService memberApplicatoinService;

    AccountBookApplicationService accountBookApplicationService;

    @BeforeEach
    void setUp() {
        this.accountBookApplicationService = new AccountBookApplicationService(
                accountBookRepository,
                accountBookHistoryRepository,
                accountBookIncomeHistoryRepository,
                memberApplicatoinService
        );
    }

    @DisplayName("가계부를 가지고 있지 않은 유저가, 가계부를 성공적으로 만드는 테스트")
    @Test
    void create_test() {
        //given
        String accountName = "나의 가계부";

        Long coupleId = 1L;
        Long memberId = 1L;

        AccountBookEntity accountBookEntity = AccountBookEntity.create(accountName, coupleId);

        given(accountBookRepository.findByCoupleId(coupleId)).willReturn(Optional.empty());
        given(accountBookRepository.save(any())).willReturn(accountBookEntity);
        given(memberApplicatoinService.isMatchedCouple(memberId, coupleId)).willReturn(true);
        //when
        AccountBook accountBook = this.accountBookApplicationService.create(accountName, memberId, coupleId);

        //then
        assertEquals(accountBook.getName(), accountName);
        assertEquals(accountBook.getCoupleId(), coupleId);
    }

    @DisplayName("가계부를 이미 가진 유저가, 가계부를 추가로 만들고자 하는 경우를 테스트")
    @Test
    void create_fail_test() {
        //given
        long coupleId = 1L;
        long memberId = 1L;

        given(accountBookRepository.findByCoupleId(coupleId))
                .willReturn(Optional.of(AccountBookEntity.create("name", coupleId)));


        //when & then
        assertThrows(AlreadyExistAccountBookException.class,
                () -> this.accountBookApplicationService.create("name", memberId, coupleId));

        then(accountBookRepository).shouldHaveNoMoreInteractions();
    }


}