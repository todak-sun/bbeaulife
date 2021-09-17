package com.todak.bbeaulife.application.couple;

import java.time.LocalDate;

public abstract class Member {

    private Long id;
    private String name;
    private String nickName;

    private LocalDate birthDay;


    protected LocalDate getBirthDay() {
        return this.birthDay;
    }

}
