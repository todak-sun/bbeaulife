package com.todak.bbeaulife.entities;

import com.todak.bbeaulife.type.CoupleRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.todak.bbeaulife.type.CoupleRole.EMPTY;

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
public class MemberEntity extends AbstractDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    @Getter
    @Id
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private CoupleRole role;

    private MemberEntity(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = CoupleRole.EMPTY;
    }

    public static MemberEntity create(String email, String password) {
        return new MemberEntity(email, password);
    }

    public void roleAs(CoupleRole role) {
        this.role = role;
    }

}
