package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.application.couple.Couple;
import lombok.Getter;

import java.util.List;

public class AccountBook {

    private Long id;

    @Getter
    private String name;

    @Getter
    private Couple couple;

    private List<AccountBookHistory> accountBookHistories;
    
    public AccountBook(Long id, String name, Couple couple) {
        this.id = id;
        this.name = name;
        this.couple = couple;
    }

}
