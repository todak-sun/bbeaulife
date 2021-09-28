package com.todak.bbeaulife.application.accountBook;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentType {
    CACH("현금"),
    CARD("카드");

    private final String description;
}
