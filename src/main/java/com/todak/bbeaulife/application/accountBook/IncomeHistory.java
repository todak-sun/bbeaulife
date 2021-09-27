package com.todak.bbeaulife.application.accountBook;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public class IncomeHistory {

    private final Long id;
    private final long amount;
    private final IncomeCategory category;
    private final IncomeCategoryLevel1 categoryLevel1;
    private final String description;
    private final LocalDate occuredDateTime;
    private final Long writtenBy;

    @QueryProjection
    public IncomeHistory(Long id, long amount, IncomeCategory category, IncomeCategoryLevel1 categoryLevel1, String description, LocalDate occuredDateTime, Long writtenBy) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.categoryLevel1 = categoryLevel1;
        this.description = description;
        this.occuredDateTime = occuredDateTime;
        this.writtenBy = writtenBy;
    }

    @Override
    public String toString() {
        return "IncomeHistory{" +
                "id=" + id +
                ", amount=" + amount +
                ", category=" + category +
                ", categoryLevel1=" + categoryLevel1 +
                ", description='" + description + '\'' +
                ", occuredDateTime=" + occuredDateTime +
                ", writtenBy=" + writtenBy +
                '}';
    }
}
