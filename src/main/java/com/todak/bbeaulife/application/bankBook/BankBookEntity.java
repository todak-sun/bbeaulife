package com.todak.bbeaulife.application.bankBook;

import com.todak.bbeaulife.entities.AbstractDateTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name = "createDateTime",
                column = @Column(name = "ENROLLED_DATE_TIME")),
        @AttributeOverride(name = "updateDateTime",
                column = @Column(name = "UPDATED_DATE_TIME"))
})
@Entity
@Table(name = "BANK_BOOK")
public class BankBookEntity extends AbstractDateTimeEntity {

    @Getter
    @Id
    @Column(name = "BANK_BOOK_ID")
    private Long id;

    @Getter
    @Column(name = "BANK", nullable = false)
    @Enumerated(EnumType.STRING)
    private Bank bank;

    @Getter
    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;

    @Getter
    @Column(name = "NICK_NAME", nullable = false)
    private String nickName;

    @Column(name = "OWNER_ID", nullable = false)
    private Long ownerId;

    private BankBookEntity(Bank bank, String accountNumber, Long ownerId, String nickName) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.ownerId = ownerId;
        this.nickName = nickName;
    }

    public static BankBookEntity create(Bank bank, String accountNumber, Long ownerId, String nickName) {
        if (Objects.isNull(nickName)) {
            return new BankBookEntity(bank, accountNumber, ownerId, bank.getBankName());
        } else {
            return new BankBookEntity(bank, accountNumber, ownerId, nickName);
        }
    }

}
