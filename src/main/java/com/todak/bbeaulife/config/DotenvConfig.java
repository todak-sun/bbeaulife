package com.todak.bbeaulife.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DotenvConfig {

    @PostConstruct
    public void initialize() {
        Properties properties = System.getProperties();
        properties.putAll(getEnviromentDotenv());
    }

    private Map<String, Object> getEnviromentDotenv() {
        Map<String, Object> env = new HashMap<>();
        ClassPathResource classPathResource = new ClassPathResource(".env");
        if (classPathResource.exists()) {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))
            ) {
                List<String> lines = reader.lines().collect(Collectors.toList());
                lines.forEach(line -> {
                    String[] keyValue = line.split("=");
                    String key = keyValue[0];
                    String value = keyValue[1];
                    env.put(key, value);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return env;
    }

}
