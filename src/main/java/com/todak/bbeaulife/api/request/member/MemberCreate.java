package com.todak.bbeaulife.api.request.member;

import com.todak.bbeaulife.type.Sex;
import com.todak.bbeaulife.validation.annotation.DomainConstraint;
import com.todak.bbeaulife.validation.annotation.PasswordConstraint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@RequiredArgsConstructor
public class MemberCreate {

    @NotEmpty(message = "이메일을 입력해주세요.")
    private final String email;

    @PasswordConstraint(pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$")
    private final PasswordGroup passwordGroup;

    @NotEmpty(message = "성을 입력해주세요")
    private final String firstName;

    @NotEmpty(message = "이름을 입력해주세요")
    private final String lastName;

    @DomainConstraint(domains = {Sex.Values.MALE, Sex.Values.FEMALE})
    private final String sex;


}
