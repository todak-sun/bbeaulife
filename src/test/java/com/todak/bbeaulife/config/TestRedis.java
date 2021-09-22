package com.todak.bbeaulife.config;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;

import java.util.Objects;

@Slf4j
public class TestRedis extends GenericContainer<TestRedis> {

    private static final String DOCKER_IMAGE_NAME = "redis:6";
    private static TestRedis instance;

    private TestRedis() {
        super(DOCKER_IMAGE_NAME);
    }

    public static TestRedis getInstance() {
        if (Objects.isNull(instance)) {
            instance = new TestRedis()
                    .withExposedPorts(6379);
        }
        return instance;
    }

    @Override
    public void start() {
        super.start();
        log.info("redis host : {}", instance.getHost());
        log.info("redis port : {}", instance.getFirstMappedPort().toString());
        System.setProperty("CONTAINER_REDIS_HOST", instance.getHost());
        System.setProperty("CONTAINER_REDIS_PORT", instance.getFirstMappedPort().toString());
    }

    @Override
    public void stop() {

    }
}
