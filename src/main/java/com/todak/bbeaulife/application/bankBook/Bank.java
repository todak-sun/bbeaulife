package com.todak.bbeaulife.application.bankBook;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Bank {
    SHINHAN("신한은행"),
    WOORI("우리은행"),
    KB("국민은행"),
    IBK("IBK기업은행"),
    KEB("하나은행"),
    SC("SC제일은행");

    @Getter
    private final String bankName;

}
