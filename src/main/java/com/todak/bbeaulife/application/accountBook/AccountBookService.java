package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.application.couple.Couple;

public class AccountBookService {

    public void create(String name, Couple couple) {
        AccountBook accountBook = AccountBook.create(name, couple);
    }






}
