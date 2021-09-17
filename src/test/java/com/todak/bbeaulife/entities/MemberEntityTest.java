package com.todak.bbeaulife.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class MemberEntityTest {

    @Test
    public void createTest() {

        AccountEntity accountEntity = new AccountEntity("tjsdydwn@gmail.com", "password");
        assertNotNull(accountEntity);
    }

}