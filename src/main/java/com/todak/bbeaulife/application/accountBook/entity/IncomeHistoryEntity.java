package com.todak.bbeaulife.application.accountBook.entity;

import com.todak.bbeaulife.application.accountBook.IncomeCategory;
import com.todak.bbeaulife.application.accountBook.IncomeCategoryLevel1;
import com.todak.bbeaulife.application.accountBook.MoneyFlow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = MoneyFlow.Values.INCOME)
@Entity
@Table(name = "ACCOUNT_BOOK_INCOME_HISTORY")
public class IncomeHistoryEntity extends AccountBookHistoryEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private IncomeCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY_LV1")
    private IncomeCategoryLevel1 categoryLevel1;

    private IncomeHistoryEntity(long amount, IncomeCategory category, IncomeCategoryLevel1 categoryLevel1, String description, LocalDate occuredDateTime, Long writtenBy, AccountBookEntity accountBook) {
        super(amount, description, occuredDateTime, writtenBy, accountBook);
        this.category = category;
        this.categoryLevel1 = categoryLevel1;
    }

    public static IncomeHistoryEntity create(long amount, IncomeCategory incomeCategory, IncomeCategoryLevel1 incomeCategoryLevel1, String description, LocalDate occuredDateTime, Long writtenBy, AccountBookEntity accountBook) {
        return new IncomeHistoryEntity(amount, incomeCategory, incomeCategoryLevel1, description, occuredDateTime, writtenBy, accountBook);
    }


    public void modify(long amount, String description, IncomeCategory incomeCategory, IncomeCategoryLevel1 incomeCategoryLevel1, LocalDate occuredDateTime, Long writerId) {
        this.amount = amount;
        this.description = description;
        this.category = incomeCategory;
        this.categoryLevel1 = incomeCategoryLevel1;
        this.occuredDateTime = occuredDateTime;
        this.writtenBy = writerId;
    }

}
