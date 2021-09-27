package com.todak.bbeaulife.application.accountBook;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IncomeCategoryLevel1 {
    MONTHLY_PATCHECK("월급"),
    BOUNS("상여"),
    DIVIDEND("배당"),
    INTEREST("이자"),
    ETC("기타");

    @Getter
    private final String description;
}