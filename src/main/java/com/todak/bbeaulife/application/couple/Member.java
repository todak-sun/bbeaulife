package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.type.Gender;
import com.todak.bbeaulife.type.Sex;

import java.time.LocalDate;

public abstract class Member {

    private Long id;
    private String name;
    private String nickName;
    private Sex sex;
    private Gender gender;

    private LocalDate birthDay;


    protected LocalDate getBirthDay() {
        return this.birthDay;
    }

}
