package com.todak.bbeaulife.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DotenvLoader {

    Map<String, String> envs;

    public DotenvLoader() {
        this.envs = getEnviromentDotenv();
    }

    public String getString(String key) {
        return this.envs.get(key);
    }

    public Integer getInteger(String key) {
        return Integer.parseInt(this.envs.get(key));
    }

    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(key);
    }


    private Map<String, String> getEnviromentDotenv() {
        Map<String, String> env = new HashMap<>();
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
