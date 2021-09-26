package com.todak.bbeaulife.application.bankBook;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BankBookTest {

    @Test
    void create_success_test() {
        //given
        Long bankBookId = 1L;
        Bank bank = Bank.KB;
        String accountNumber = "010-111-1111";
        String nickName = "국민은행계좌";

        //when
        BankBook result = BankBook.create(bankBookId, bank, accountNumber, nickName);

        //then
        assertNotNull(result, "null이 아니다");
        assertEquals(bankBookId, result.getId(), "야이디가 같다");
        assertEquals(result.getBank(), bank, "은행이 같다");
        assertEquals(accountNumber, result.getAccountNumber(), "계좌번호가 같다");
        assertEquals(nickName, result.getNickName(), "별칭이 같다");
    }


}