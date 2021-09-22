package com.todak.bbeaulife.repository;

import com.todak.bbeaulife.application.couple.MemberRepository;
import com.todak.bbeaulife.config.WithContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
class MemberEntityRepositoryTest extends WithContainer {
    
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void is_bean() {
        assertNotNull(memberRepository);
    }

    @Test
    public void saveTest() {

    }

}