package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.member.Member;
import lombok.Getter;

public class Couple {

    @Getter
    private Long id;

    private Member husband;
    private Member wife;

    public Couple(Long id, Member husband, Member wife) {
        this.id = id;
        this.husband = husband;
        this.wife = wife;
    }
}
