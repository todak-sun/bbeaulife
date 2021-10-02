package com.todak.bbeaulife.application.accountBook;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "ACCOUNT_BOOK_INCOME_HISTORY")
@DiscriminatorValue(value = MoneyFlow.Values.INCOME)
public class AccountBookIncomeHistoryEntity extends AccountBookHistoryEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "INCOME_CATEGORY")
    private IncomeCategory incomeCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "INCOME_CATEGORY_LV1")
    private IncomeCategoryLevel1 incomeCategoryLevel1;

    private AccountBookIncomeHistoryEntity(long amount, IncomeCategory incomeCategory, IncomeCategoryLevel1 incomeCategoryLevel1, String description, LocalDate occuredDateTime, Long writtenBy, AccountBookEntity accountBook) {
        super(amount, description, occuredDateTime, writtenBy, accountBook);
        this.incomeCategory = incomeCategory;
        this.incomeCategoryLevel1 = incomeCategoryLevel1;
    }

    public static AccountBookIncomeHistoryEntity create(long amount, IncomeCategory incomeCategory, IncomeCategoryLevel1 incomeCategoryLevel1, String description, LocalDate occuredDateTime, Long writtenBy, AccountBookEntity accountBook){
        return new AccountBookIncomeHistoryEntity(amount, incomeCategory, incomeCategoryLevel1, description, occuredDateTime, writtenBy, accountBook);
    }


    public void modify(long amount, String description, IncomeCategory incomeCategory, IncomeCategoryLevel1 incomeCategoryLevel1, LocalDate occuredDateTime, Long writerId) {
        this.amount = amount;
        this.description = description;
        this.incomeCategory = incomeCategory;
        this.incomeCategoryLevel1 = incomeCategoryLevel1;
        this.occuredDateTime = occuredDateTime;
        this.writtenBy = writerId;
    }

}
