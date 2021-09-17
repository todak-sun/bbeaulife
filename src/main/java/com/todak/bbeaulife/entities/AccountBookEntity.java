package com.todak.bbeaulife.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Getter
    @Column(name = "ACCOUNT_BOOK_ID")
    private Long id;

    @Getter
    @Column(name = "NAME")
    private String name;

    @Getter
    @Column(name = "COUPLE_ID", unique = true)
    private Long coupleId;

    public static AccountBookEntity create(String name, Long coupleId) {
        AccountBookEntity entity = new AccountBookEntity();
        entity.name = name;
        entity.coupleId = coupleId;
        return entity;
    }

}
