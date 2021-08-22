package com.todak.bbeaulife.repository;

import com.todak.bbeaulife.entities.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void saveTest() {
        Account account = new Account("tjsdydwn@gmail.com", "password");
        accountRepository.save(account);
    }

}