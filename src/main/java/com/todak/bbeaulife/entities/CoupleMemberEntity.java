package com.todak.bbeaulife.entities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "COUPLE_MEMBER")
public class CoupleMemberEntity {

    @Id
    @Column(name = "COUPLE_MEMBER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUPLE_ID")
    private CoupleEntity couple;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private MemberEntity member;

    private CoupleMemberEntity(CoupleEntity couple, MemberEntity member) {
        this.couple = couple;
        this.member = member;
    }

    public static CoupleMemberEntity create(CoupleEntity couple, MemberEntity member) {
        return new CoupleMemberEntity(couple, member);
    }


}
