package com.todak.bbeaulife.entities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ANNIVERSARY")
public class AnniversaryEntity extends AbstractDateTimeEntity {

    @Id
    @Column(name = "ANNIVERSARY_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "_WHEN")
    private LocalDateTime when;

    @Column(name = "COUPLE_ID")
    private Long coupleId;

    @Column(name = "ENROLLED_BY")
    private Long enrolledBy;


}
