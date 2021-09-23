package com.todak.bbeaulife;

import com.todak.bbeaulife.config.WithContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class BbeaulifeApiApplicationTests extends WithContainer {

    @Test
    void contextLoads() {
    }

}
