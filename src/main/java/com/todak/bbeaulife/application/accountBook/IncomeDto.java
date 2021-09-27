package com.todak.bbeaulife.application.accountBook;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class IncomeDto {

    private final long amount;
    private final IncomeCategory incomeCategory;
    private final IncomeCategoryLevel1 incomeCategoryLevel1;
    private final String description;
    private final LocalDate occuredDateTime;

    public IncomeDto(long amount, IncomeCategory incomeCategory, IncomeCategoryLevel1 incomeCategoryLevel1, String description, LocalDate occuredDateTime) {
        this.amount = amount;
        this.incomeCategory = incomeCategory;
        this.incomeCategoryLevel1 = incomeCategoryLevel1;
        this.description = description;
        this.occuredDateTime = occuredDateTime;
    }
}
