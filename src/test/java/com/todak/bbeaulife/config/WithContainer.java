package com.todak.bbeaulife.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class WithContainer {

    static TestPostgreSQL testPostgreSQL;
    static TestRedis testRedis;

    @BeforeAll
    public static void beforeAll() {
        testPostgreSQL = TestPostgreSQL.getInstance();
        testRedis = TestRedis.getInstance();
        testPostgreSQL.start();
        testRedis.start();
    }

    @AfterAll
    public static void afterAll() {
        testPostgreSQL.stop();
        testRedis.stop();
    }


}
