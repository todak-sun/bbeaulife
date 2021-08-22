package com.todak.bbeaulife.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class AccountTest {

    @Test
    public void createTest() {

        Account account = new Account("tjsdydwn@gmail.com", "password");
        assertNotNull(account);
    }

}