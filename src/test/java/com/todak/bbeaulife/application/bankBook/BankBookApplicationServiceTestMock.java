package com.todak.bbeaulife.application.bankBook;

import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class BankBookApplicationServiceTestMock {

    BankBookApplicationService bankBookApplicationService;

    @Mock
    BankBookRepository bankBookRepository;

    @Mock
    MemberApplicatoinService memberApplicatoinService;

    @BeforeEach
    void setUp() {
        this.bankBookApplicationService = new BankBookApplicationService(
                bankBookRepository,
                memberApplicatoinService
        );
    }

    @Test
    void enroll_success() {
        //given
        long ownerId = 1L;
        long bankBookId = 2L;
        Bank bank = Bank.IBK;
        String accountNumber = "010-123-123";
        String bankBookNickName = "월급통장";

        BankBookEntity spyBankBookEntity = spy(BankBookEntity.create(bank, accountNumber, ownerId, bankBookNickName));
        given(spyBankBookEntity.getId()).willReturn(bankBookId);
        given(memberApplicatoinService.exsistById(ownerId)).willReturn(true);
        given(bankBookRepository.save(any())).willReturn(spyBankBookEntity);

        //when
        BankBook result = bankBookApplicationService.enrollBankBook(ownerId, bank, accountNumber, bankBookNickName);

        //then
        assertNotNull(result, "null이 아니다");
        assertEquals(bankBookId, result.getId());
        assertEquals(bankBookNickName, result.getNickName());
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(bank, result.getBank());

    }

}