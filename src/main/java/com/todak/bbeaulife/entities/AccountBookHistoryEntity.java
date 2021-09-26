package com.todak.bbeaulife.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT_BOOK_HISTORY")
public class AccountBookHistoryEntity {

    @Id
    private Long id;

    private String description;

    private long amount;

}
