package com.todak.bbeaulife.validation;

import com.todak.bbeaulife.type.PasswordForm;
import com.todak.bbeaulife.validation.annotation.PasswordConstraint;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PasswordFormValidatorTest extends AbstractValidatorTest {

    @Test
    void success() {
        //given
        Tester tester = createTester("tjasd@124", "tjasd@124");

        //when
        Set<ConstraintViolation<Tester>> violations = this.validate(tester);

        //then
        assertTrue(violations.isEmpty(), "유효성 검사에 무사히 통과에 반환된 에러가 없다.");
    }

    @DisplayName("password를 입력받지 못했을 경우 실패")
    @Test
    void fail_when_no_password() {
        // given
        Tester tester = createTester(null, null);

        // when
        Set<ConstraintViolation<Tester>> violations = this.validate(tester);

        // then
        assertFalse(violations.isEmpty(), "에러가 있다.");
        assertEquals(1, violations.size(), "에러는 하나다");

        for (ConstraintViolation<Tester> violation : violations) {
            log.info("violation : {}", violation);
        }
    }

    @DisplayName("비밀번호와 재입력한 비밀번호가 서로 다르면 실패")
    @Test
    void fail_when_not_same_passwords() {
        //given
        Tester tester = createTester("valid@1234", "valid@12345");

        //when
        Set<ConstraintViolation<Tester>> violations = this.validate(tester);

        //then
        assertFalse(violations.isEmpty(), "에러가 있다.");
        assertEquals(1, violations.size(), "에러는 하나다");
    }


    private Tester createTester(String password, String repetedPassword) {
        Tester tester = new Tester();
        PasswordTester passwordTester = new PasswordTester();
        passwordTester.password = password;
        passwordTester.repeatedPassword = repetedPassword;
        tester.passwordTester = passwordTester;
        return tester;
    }

    static class Tester {
        @PasswordConstraint(
                pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
                message = "비밀번호는 영 대/소문자로 시작하며, 숫자, 특수문자가 각 한 개씩 포함된 8 ~ 16자로 설정 가능합니다.")
        PasswordTester passwordTester;
    }

    @Getter
    static class PasswordTester implements PasswordForm {
        String password;
        String repeatedPassword;
    }

}