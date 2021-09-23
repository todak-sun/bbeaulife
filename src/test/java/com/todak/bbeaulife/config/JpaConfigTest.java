package com.todak.bbeaulife.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
class JpaConfigTest extends WithContainer {

    @Autowired
    ApplicationContext ctx;

    @DisplayName("jpaQueryFactory가 bean으로 등록되어 있다")
    @Test
    public void use_query_dsl() {
        JPAQueryFactory bean = ctx.getBean(JPAQueryFactory.class);
        assertNotNull(bean);
    }

}