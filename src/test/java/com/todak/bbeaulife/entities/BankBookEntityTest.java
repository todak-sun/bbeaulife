package com.todak.bbeaulife.entities;

import com.todak.bbeaulife.application.bankBook.Bank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BankBookEntityTest {

    @DisplayName("정상 생성 테스트")
    @Test
    void create_success() {

        Bank bank = Bank.KB;
        String accountNumber = "123-123-123";
        long ownerId = 1L;
        String nickName = "국민은행계좌";

        BankBookEntity result = BankBookEntity.create(bank, accountNumber, ownerId, nickName);

        assertNotNull(result, "null이 아니다");
        assertEquals(bank, result.getBank(), "은행이 같다");
        assertEquals(accountNumber, result.getAccountNumber(), "계좌번호가 같다");
        assertEquals(nickName, result.getNickName(), "별칭이 같다");
    }

    @DisplayName("은행 별칭을 따로 지정하지 않아도, 은행의 이름이 자동으로 지정되어 생성")
    @Test
    void create_success_when_no_nickname() {
        Bank bank = Bank.KB;
        String accountNumber = "123-123-123";
        long ownerId = 1L;
        String nickName = null;

        BankBookEntity result = BankBookEntity.create(bank, accountNumber, ownerId, nickName);

        assertNotNull(result, "null이 아니다");
        assertEquals(bank, result.getBank(), "은행이 같다");
        assertEquals(accountNumber, result.getAccountNumber(), "계좌번호가 같다");
        assertEquals(bank.getBankName(), result.getNickName(), "별칭의 기본값으로, 은행의 이름이 들어간다.");
    }

}