package com.todak.bbeaulife.application.accountBook;

import lombok.Getter;

import java.util.List;

public class AccountBook {

    @Getter
    private final Long id;

    @Getter
    private final String name;

    @Getter
    private final Long coupleId;

    private List<AccountBookHistory> accountBookHistories;

    public AccountBook(Long id, String name, Long coupleId) {
        this.id = id;
        this.name = name;
        this.coupleId = coupleId;
    }

}
