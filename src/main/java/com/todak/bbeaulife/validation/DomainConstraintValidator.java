package com.todak.bbeaulife.validation;

import com.todak.bbeaulife.validation.annotation.DomainConstraint;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidatorContext;
import java.util.Set;

@Slf4j
public class DomainConstraintValidator extends AbstractConstraintValidator<DomainConstraint, String> {

    private Set<String> domains;

    @Override
    public void initialize(DomainConstraint constraintAnnotation) {
        this.domains = Set.of(constraintAnnotation.domains());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (domains.isEmpty()) {
            return true;
        }
        if (!domains.contains(value)) {
            this.addConstraintViolationIfNotDefaultMessage(context, String.format("available values : [%s]", String.join(",", domains)));
            return false;
        }
        return true;
    }

}
