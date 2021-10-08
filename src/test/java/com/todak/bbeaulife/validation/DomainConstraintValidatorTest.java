package com.todak.bbeaulife.validation;

import com.todak.bbeaulife.validation.annotation.DomainConstraint;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class DomainConstraintValidatorTest extends AbstractValidatorTest {


    private static final String defaultMessage = "default";


    @DisplayName("성공 케이스")
    @Test
    void validate_sucess() {
        // given
        NoDefaultMessage noDefaultMessage = new NoDefaultMessage();
        noDefaultMessage.target = "A";

        // when
        Set<ConstraintViolation<NoDefaultMessage>> validate = this.validate(noDefaultMessage);

        // then
        assertTrue(validate.isEmpty(), "실패한 케이스가 없다.");
    }

    @DisplayName("도메인에 담겨있지 않은 값을 넣으면, Violation이 추가된다.")
    @Test
    void validate_fail_when_no_contain_value() {
        // given
        NoDefaultMessage noDefaultMessage = new NoDefaultMessage();
        noDefaultMessage.target = "C";

        // when
        Set<ConstraintViolation<NoDefaultMessage>> violations = this.validate(noDefaultMessage);

        // then
        assertFalse(violations.isEmpty(), "실패한 케이스가 한 개 존재한다.");
        assertEquals(1, violations.size());

        for (ConstraintViolation<NoDefaultMessage> violation : violations) {
            assertNotNull(violation.getMessage());
            assertEquals(noDefaultMessage.target, violation.getInvalidValue());
            assertEquals("target", violation.getPropertyPath().toString());
        }
    }

    @DisplayName("Annotation에 message를 주면, 해당 메시지가 들어간다.")
    @Test
    void about_message() {
        // given
        DefaultMessage noDefaultMessage = new DefaultMessage();
        noDefaultMessage.target = "C";

        // when
        Set<ConstraintViolation<DefaultMessage>> violations = this.validate(noDefaultMessage);

        // then
        assertEquals(1, violations.size());
        for (ConstraintViolation<DefaultMessage> violation : violations) {
            assertNotNull(violation.getMessage());
            assertEquals(defaultMessage, violation.getMessage());
            assertEquals(noDefaultMessage.target, violation.getInvalidValue());
            assertEquals("target", violation.getPropertyPath().toString());
        }
    }


    static class NoDefaultMessage {
        @DomainConstraint(domains = {"A", "B"})
        String target;
    }

    static class DefaultMessage {
        @DomainConstraint(domains = {"A", "B"}, message = defaultMessage)
        String target;
    }


}