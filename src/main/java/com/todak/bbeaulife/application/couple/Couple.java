package com.todak.bbeaulife.application.couple;

import lombok.Getter;

public class Couple {

    @Getter
    private Long id;

    private Husband husband;
    private Wife wife;

    public Couple(Long id, Husband husband, Wife wife) {
        this.id = id;
        this.husband = husband;
        this.wife = wife;
    }
}
