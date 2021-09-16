package com.todak.bbeaulife.repository;

import com.todak.bbeaulife.entities.AccountEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountEntityRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void saveTest() {
        AccountEntity accountEntity = new AccountEntity("tjsdydwn@gmail.com", "password");
        accountRepository.save(accountEntity);
    }

}