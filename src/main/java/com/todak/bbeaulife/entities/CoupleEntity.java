package com.todak.bbeaulife.entities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name = "createDateTime",
                column = @Column(name = "RELATED_DATE_TIME")),
        @AttributeOverride(name = "updateDateTime",
                column = @Column(name = "UPDATED_DATE_TIME"))
})
@Entity
@Table(name = "COUPLE")
public class CoupleEntity extends AbstractDateTimeEntity {

    @Id
    @Column(name = "COUPLE_ID")
    private Long id;

    @Column(name = "WIFE_ID", nullable = false)
    private Long wifeId;

    @Column(name = "HUSBAND_ID", nullable = false)
    private Long husbandId;

    @Column(name = "NICK_NAME", unique = true)
    private String nickName;

    @Column(name = "WEDDING_DAY")
    private LocalDate weddingDay;

    @Column(name = "DECLARATION_DATE_OF_MARRIAGE")
    private LocalDate declarationDateOfMarriage;

}
