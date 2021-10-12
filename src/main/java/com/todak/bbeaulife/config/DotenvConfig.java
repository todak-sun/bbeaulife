package com.todak.bbeaulife.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class DotenvConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
        systemEnvironment.putAll(getEnviromentDotenv());
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
        log.info("env : {}", env);
        return env;
    }

}
