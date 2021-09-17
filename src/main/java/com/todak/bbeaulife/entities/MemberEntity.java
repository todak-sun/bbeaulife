package com.todak.bbeaulife.entities;

import com.todak.bbeaulife.type.CoupleRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name = "createDateTime",
                column = @Column(name = "ENROLLED_DATE_TIME")),
        @AttributeOverride(name = "updateDateTime",
                column = @Column(name = "UPDATED_DATE_TIME"))
})
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "MEMBER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MemberEntity extends AbstractDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    @Id
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private CoupleRole role;

    @OneToMany
    @JoinColumn(name = "COUPLE_MEMBER_ID")
    private List<CoupleMemberEntity> coupleMember;

    public CoupleMemberEntity relate(CoupleEntity couple) {
        CoupleMemberEntity coupleMemberEntity = CoupleMemberEntity.create(couple, this);
        this.coupleMember.add(coupleMemberEntity);
        return coupleMemberEntity;
    }


}
