package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.entities.AbstractDateTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name = "createDateTime",
                column = @Column(name = "CREATED_DATE_TIME")),
        @AttributeOverride(name = "updateDateTime",
                column = @Column(name = "UPDATED_DATE_TIME"))
})
@Entity
@Table(name = "ACCOUNT_BOOK")
public class AccountBookEntity extends AbstractDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Column(name = "ACCOUNT_BOOK_ID")
    private Long id;

    @Getter
    @Column(name = "NAME")
    private String name;

    @Getter
    @Column(name = "COUPLE_ID", unique = true)
    private Long coupleId;

    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "accountBook")
    private List<AccountBookHistoryEntity> histories = new ArrayList<>();

    public void writeIncome(long amount, IncomeCategory incomeCategory, IncomeCategoryLevel1 incomeCategoryLevel1, String description, LocalDate occuredDateTime, Long writtenBy) {
        this.histories.add(AccountBookHistoryEntity.income(amount, incomeCategory, incomeCategoryLevel1, description, occuredDateTime, writtenBy, this));
    }

    public static AccountBookEntity create(String name, Long coupleId) {
        AccountBookEntity entity = new AccountBookEntity();
        entity.name = name;
        entity.coupleId = coupleId;
        return entity;
    }

}
