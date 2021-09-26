package com.todak.bbeaulife.application.accountBook;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class IncomeCategoryLevel1Test {

    @Test
    void print_all_categories() {
        IncomeCategory.fetchAllCategories()
                .forEach(desc -> log.info("description : {}", desc));
    }

}