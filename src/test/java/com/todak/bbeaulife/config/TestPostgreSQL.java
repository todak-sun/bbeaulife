package com.todak.bbeaulife.config;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Objects;

@Slf4j
public class TestPostgreSQL extends PostgreSQLContainer<TestPostgreSQL> {

    private static final String DOCKER_IMAGE_NAME = "postgres:13.4";

    private static TestPostgreSQL instance;

    private TestPostgreSQL() {
        super(DOCKER_IMAGE_NAME);
    }

    public static TestPostgreSQL getInstance() {
        if (Objects.isNull(instance)) {
            instance = new TestPostgreSQL();
        }
        return instance;
    }

    @Override
    public void start() {
        super.start();
        log.info("jdbc url : {}", instance.getJdbcUrl());
        log.info("username : {}", instance.getUsername());
        log.info("password : {}", instance.getPassword());
        System.setProperty("CONTAINER_DB_URL", instance.getJdbcUrl());
        System.setProperty("CONTAINER_DB_USERNAME", instance.getUsername());
        System.setProperty("CONTAINER_DB_PASSWORD", instance.getPassword());
    }

    @Override
    public void stop() {

    }

}
