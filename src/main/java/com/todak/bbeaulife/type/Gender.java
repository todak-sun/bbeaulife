package com.todak.bbeaulife.type;

public enum Gender {

    HETEROSEXUAL("동성애자"),
    HOMOSEXUAL("이성애자");

    private final String typicalName;

    Gender(String typicalName) {
        this.typicalName = typicalName;
    }
}
