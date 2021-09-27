package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.entities.AbstractDateTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name = "createDateTime",
                column = @Column(name = "CREATED_DATE_TIME")),
        @AttributeOverride(name = "updateDateTime",
                column = @Column(name = "UPDATED_DATE_TIME"))
})
@Entity
@Table(name = "ACCOUNT_BOOK_HISTORY")
public class AccountBookHistoryEntity extends AbstractDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUT_BOOK_HISTORY_ID")
    private Long id;

    @Column(name = "FLOW")
    @Enumerated(EnumType.STRING)
    private MoneyFlow flow;

    @Column(name = "AMOUNT")
    private long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "INCOME_CATEGORY")
    private IncomeCategory incomeCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "INCOME_CATEGORY_LV1")
    private IncomeCategoryLevel1 incomeCategoryLevel1;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "OCCURED_DATE_TIME")
    private LocalDate occuredDateTime;

    @Column(name = "WRITER_ID")
    private Long writtenBy;

    @JoinColumn(name = "ACCOUNT_BOOK_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountBookEntity accountBook;

    private AccountBookHistoryEntity(MoneyFlow moneyFlow, long amount, IncomeCategory incomeCategory, IncomeCategoryLevel1 incomeCategoryLevel1, String description, LocalDate occuredDateTime, Long writtenBy, AccountBookEntity accountBook) {
        this.flow = moneyFlow;
        this.amount = amount;
        this.incomeCategory = incomeCategory;
        this.incomeCategoryLevel1 = incomeCategoryLevel1;
        this.description = description;
        this.occuredDateTime = occuredDateTime;
        this.writtenBy = writtenBy;
        this.accountBook = accountBook;
    }

    public static AccountBookHistoryEntity income(long amount, IncomeCategory incomeCategory, IncomeCategoryLevel1 incomeCategoryLevel1, String description, LocalDate occuredDateTime, Long writtenBy, AccountBookEntity accountBook) {
        return new AccountBookHistoryEntity(MoneyFlow.INCOME, amount, incomeCategory, incomeCategoryLevel1, description, occuredDateTime, writtenBy, accountBook);
    }

}
