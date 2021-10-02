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
@DiscriminatorColumn(name = "FLOW", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "ACCOUNT_BOOK_HISTORY")
public abstract class AccountBookHistoryEntity extends AbstractDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUT_BOOK_HISTORY_ID")
    protected Long id;

    @Column(name = "AMOUNT")
    protected long amount;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "OCCURED_DATE_TIME")
    protected LocalDate occuredDateTime;

    @Column(name = "WRITER_ID")
    protected Long writtenBy;

    @JoinColumn(name = "ACCOUNT_BOOK_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    protected AccountBookEntity accountBook;

    protected AccountBookHistoryEntity(long amount, String description, LocalDate occuredDateTime, Long writtenBy, AccountBookEntity accountBook) {
        this.amount = amount;
        this.description = description;
        this.occuredDateTime = occuredDateTime;
        this.writtenBy = writtenBy;
        this.accountBook = accountBook;
    }

}
