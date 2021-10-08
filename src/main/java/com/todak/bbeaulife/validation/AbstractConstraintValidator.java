package com.todak.bbeaulife.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Objects;

public abstract class AbstractConstraintValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    protected void addConstraintViolationIfNotDefaultMessage(ConstraintValidatorContext context, String message) {
        if (this.hasNotDefaultMessage(context)) {
            this.addConstraintViolation(context, message);
        }
    }

    protected void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
    
    private boolean hasNotDefaultMessage(ConstraintValidatorContext context) {
        return Objects.isNull(context.getDefaultConstraintMessageTemplate())
                || context.getDefaultConstraintMessageTemplate().isBlank();
    }

}
