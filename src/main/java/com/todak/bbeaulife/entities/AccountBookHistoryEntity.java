package com.todak.bbeaulife.entities;

import com.todak.bbeaulife.application.accountBook.MoneyFlow;
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
    @Column(name = "ACCOUT_BOOK_HISTORY_ID")
    private Long id;

    @Column(name = "AMOUNT")
    private long amount;

    @Column(name = "MONEY_FLOW")
    @Enumerated(EnumType.STRING)
    private MoneyFlow flow;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "OCCURED_DATE_TIME")
    private LocalDate occuredDateTime;




}
