package com.todak.bbeaulife.application.creditcard;

import javax.persistence.*;

@Entity
@Table(name = "CREDIT_CARD")
public class CreditCardEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "CREDIT_CARD_ID")
    private Long id;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(name = "VENDOR")
    private CreditCard vendor;

    @Column(name = "OWNER_ID")
    private Long ownerId;

}
