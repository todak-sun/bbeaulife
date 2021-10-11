package com.todak.bbeaulife.validation.annotation;

import com.todak.bbeaulife.validation.PasswordFormValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordFormValidator.class)
public @interface PasswordGroupConstraint {

    String message() default "";

    String pattern() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
