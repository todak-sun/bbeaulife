package com.todak.bbeaulife.application.accountBook.entity;

import com.todak.bbeaulife.application.accountBook.ExpenditureCategory;
import com.todak.bbeaulife.application.accountBook.ExpenditureCategoryLevel1;
import com.todak.bbeaulife.application.accountBook.MoneyFlow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = MoneyFlow.Values.EXPENDITURE)
@Entity
@Table(name = "ACCOUNT_BOOK_EXPENDITURE_HISTORY")
public class ExpenditureHistoryEntity extends AccountBookHistoryEntity {

    private ExpenditureCategory category;

    private ExpenditureCategoryLevel1 categoryLevel1;

    public ExpenditureHistoryEntity(long amount, String description, LocalDate occuredDateTime, Long writtenBy, AccountBookEntity accountBook, ExpenditureCategory category, ExpenditureCategoryLevel1 categoryLevel1) {
        super(amount, description, occuredDateTime, writtenBy, accountBook);
        this.category = category;
        this.categoryLevel1 = categoryLevel1;
    }
}
