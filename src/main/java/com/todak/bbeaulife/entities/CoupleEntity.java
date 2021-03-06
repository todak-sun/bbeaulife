package com.todak.bbeaulife.entities;

import lombok.AccessLevel;
import lombok.Getter;
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

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COUPLE_ID")
    private Long id;

    @Column(name = "NICK_NAME", unique = true)
    private String nickName;

    @Column(name = "WEDDING_DAY")
    private LocalDate weddingDay;

    @Column(name = "DECLARATION_DATE_OF_MARRIAGE")
    private LocalDate declarationDateOfMarriage;

    public static CoupleEntity create() {
        return new CoupleEntity();
    }

}
