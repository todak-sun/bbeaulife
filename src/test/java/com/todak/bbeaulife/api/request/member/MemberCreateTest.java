package com.todak.bbeaulife.api.request.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class MemberCreateTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validation_sucess() {
        MemberCreate male = new MemberCreate(null, null, null, null, "MALE");
        Set<ConstraintViolation<MemberCreate>> validate = validator.validate(male);
        assertTrue(validate.isEmpty());
    }

    @Test
    void validation_fail(){
        MemberCreate notExsist = new MemberCreate(null, null, null, null, "NOT_EXSIST");
        Set<ConstraintViolation<MemberCreate>> validate = validator.validate(notExsist);
        assertFalse(validate.isEmpty());

        log.info("validate size : {}", validate.size());

        for (ConstraintViolation<MemberCreate> violation : validate) {
            log.info("message : {}", violation.getMessage());
            log.info("message template : {}", violation.getMessageTemplate());
        }
    }

}