package com.todak.bbeaulife.application.accountBook;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExpenditureCetory {
    APPAREL("의류비"),
    FOOD("식비"),
    HOUSING("주거비"),
    COMMUNICATION("통신비"),
    EDUCATION("교육비"),
    HEALTH_CARE("의료비"),
    ENTERTAINMENT("교육비");

    private final String description;

}
