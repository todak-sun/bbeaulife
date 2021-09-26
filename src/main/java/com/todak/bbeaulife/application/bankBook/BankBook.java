package com.todak.bbeaulife.application.bankBook;


import lombok.Getter;

public class BankBook {

    @Getter
    private final Long id;
    @Getter
    private final Bank bank;
    @Getter
    private final String accountNumber;
    @Getter
    private final String nickName;

    private BankBook(Long id, Bank bank, String accountNumber, String nickName) {
        this.id = id;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.nickName = nickName;
    }

    public static BankBook create(Long id, Bank bank, String accountNumber, String nickName) {
        return new BankBook(id, bank, accountNumber, nickName);
    }

}
