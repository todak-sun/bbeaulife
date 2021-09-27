package com.todak.bbeaulife.application.accountBook;

import java.time.LocalDate;


public record IncomeDto(long amount, IncomeCategory incomeCategory,
                        IncomeCategoryLevel1 incomeCategoryLevel1,
                        String description, LocalDate occuredDateTime) {



}
