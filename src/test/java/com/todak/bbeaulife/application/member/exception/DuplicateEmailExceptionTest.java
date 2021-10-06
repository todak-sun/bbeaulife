package com.todak.bbeaulife.application.member.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateEmailExceptionTest {

    @DisplayName("이메일이 같은지 테스트")
    @Test
    void creation() {
        String email = "eamil@email.com";
        DuplicateEmailException duplicateEmailException = new DuplicateEmailException(email);

        assertEquals(email, duplicateEmailException.getEmail(), "에러 생성시 입력한 email과, 에러 객체 내에 email이 동일하다.");
    }

}