package com.todak.bbeaulife.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class DotenvLoaderTest extends WithContainer {


    @Test
    void read_resource_path() {

        Map<String, String> getenv = System.getenv();
        getenv.entrySet()
                .forEach(entry -> {
                    log.info("ENV {}={}", entry.getKey(), entry.getValue());
                });

        System.getProperties().entrySet()
                .forEach(entry -> {
                    log.info("PROP {}={}", entry.getKey(), entry.getValue());
                });
    }

}