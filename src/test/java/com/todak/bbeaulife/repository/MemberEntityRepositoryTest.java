package com.todak.bbeaulife.repository;

import com.todak.bbeaulife.application.couple.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberEntityRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void saveTest() {

    }

}