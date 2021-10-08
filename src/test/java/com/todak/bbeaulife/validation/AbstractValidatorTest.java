package com.todak.bbeaulife.validation;

import org.junit.jupiter.api.BeforeAll;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


abstract class AbstractValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    protected <T> Set<ConstraintViolation<T>> validate(T value, Class<?>... groups) {
        return validator.validate(value, groups);
    }

}
