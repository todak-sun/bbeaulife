package com.todak.bbeaulife.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Sex {

    MALE(Values.MALE, "남성"),
    FEMALE(Values.FEMALE, "여성");

    @Getter
    private final String name;
    private final String description;

    public static class Values {
        public static final String MALE = "MALE";
        public static final String FEMALE = "FEMALE";
    }

}
