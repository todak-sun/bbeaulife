package com.todak.bbeaulife.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class AccountEntityTest {

    @Test
    public void createTest() {

        AccountEntity accountEntity = new AccountEntity("tjsdydwn@gmail.com", "password");
        assertNotNull(accountEntity);
    }

}