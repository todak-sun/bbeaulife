package com.todak.bbeaulife.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class StringCastTest {

    @Test
    void int_cast_test() {
        String ok = "123";
        int num = Integer.parseInt(ok);
        log.info("i : {}", num);

        ok = "not";
//        int not = Integer.parseInt(ok);
//        log.info("not : {}", not);
    }

    @Test
    void double_cast_test() {
        String ok = "123.23";
        double num = Double.parseDouble(ok);
        log.info("num : {}", num);

    }

    @Test
    void boolean_cast_test() {
        String trueStr = "true";
        boolean b = Boolean.parseBoolean(trueStr);
        log.info("b : {}", b);

        boolean b1 = Boolean.parseBoolean("aisjdf;oaisjdf");
        log.info("b1 : {}", b1);
    }

}
