package com.todak.bbeaulife.application.accountBook;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentType {
    CASH("ํ๊ธ"),
    CARD("์นด๋");

    private final String description;
}
