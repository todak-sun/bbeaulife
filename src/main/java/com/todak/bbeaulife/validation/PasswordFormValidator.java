package com.todak.bbeaulife.validation;

import com.todak.bbeaulife.type.PasswordForm;
import com.todak.bbeaulife.validation.annotation.PasswordConstraint;

import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;

public class PasswordFormValidator extends AbstractConstraintValidator<PasswordConstraint, PasswordForm> {

    private Pattern pattern;

    @Override
    public void initialize(PasswordConstraint passwordConstraint) {
        this.pattern = Pattern.compile(passwordConstraint.pattern());
    }

    @Override
    public boolean isValid(PasswordForm form, ConstraintValidatorContext context) {
        String password = form.getPassword();
        if (Objects.isNull(password) || password.isBlank()) {
            return false;
        }

        if (!this.pattern.matcher(password).matches()) {
            return false;
        }

        if (!password.equals(form.getRepeatedPassword())) {
            addConstraintViolation(context, "비밀번호와 재입력한 비밀번호가 서로 일치하지 않습니다.");
            return false;
        }


        return true;
    }


}
