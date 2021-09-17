package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.application.couple.Couple;

import java.util.List;

public class AccountBook {

    private Long id;
    private String name;
    private Couple couple;

    private List<AccountBookHistory> accountBookHistories;

    private AccountBook(String name, Couple couple) {
        this.name = name;
        this.couple = couple;
    }

    public static AccountBook create(String name, Couple couple) {
        return new AccountBook(name, couple);
    }

}
