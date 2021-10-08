package com.todak.bbeaulife.api.request.member;

import com.todak.bbeaulife.type.PasswordForm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PasswordGroup implements PasswordForm {

    private final String password;
    private final String repeatedPassword;

}
