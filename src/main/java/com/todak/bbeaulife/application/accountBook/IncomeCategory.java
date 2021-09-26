package com.todak.bbeaulife.application.accountBook;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum IncomeCategory {

    PAY("근로소득",
            List.of(IncomeCategoryLevel1.MONTHLY_PATCHECK,
                    IncomeCategoryLevel1.BOUNS)),
    FINANCIAL("금융소득",
            List.of(IncomeCategoryLevel1.DIVIDEND,
                    IncomeCategoryLevel1.INTEREST)),
    ETC("기타",
            List.of(IncomeCategoryLevel1.ETC)),
    ;

    private final String description;
    private final List<IncomeCategoryLevel1> childrens;

    public static List<String> fetchAllCategories() {
        return Arrays.stream(values())
                .flatMap(lv0 -> lv0.childrens.stream().map(lv1 -> String.join(" > ", lv0.description, lv1.description)))
                .collect(Collectors.toList());
    }


    @RequiredArgsConstructor
    public enum IncomeCategoryLevel1 {
        MONTHLY_PATCHECK("월급"),
        BOUNS("상여"),
        DIVIDEND("배당"),
        INTEREST("이자"),
        ETC("기타");

        private final String description;
    }


}
