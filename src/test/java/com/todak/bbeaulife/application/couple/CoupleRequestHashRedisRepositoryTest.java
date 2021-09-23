package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.config.WithContainer;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class CoupleRequestHashRedisRepositoryTest extends WithContainer {

    @Autowired
    CoupleRequestRedisRepository coupleRequestRedisRepository;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void saveTest() {
        //given
        CoupleRequestHash origin = CoupleRequestHash.create(1L, 2L, CoupleRole.HUSBAND);

        //when
        CoupleRequestHash saved = coupleRequestRedisRepository.save(origin);

        //then
        assertEquals(origin.getRequesterId(), saved.getRequesterId());
        assertEquals(origin.getRequesteeId(), saved.getRequesteeId());
        assertEquals(origin.getRole(), saved.getRole());

        Long timeOut = redisTemplate.getExpire(String.join(":", "coupleRequest", saved.getRequesterId().toString()));
        assertEquals(saved.getTimeout(), timeOut);

    }

}