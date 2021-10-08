package com.todak.bbeaulife.entities;

import com.todak.bbeaulife.type.CoupleRole;
import com.todak.bbeaulife.type.FullName;
import com.todak.bbeaulife.type.Sex;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

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

    @Getter
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Getter
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "FIRST_NAME")),
            @AttributeOverride(name = "lastName", column = @Column(name = "LAST_NAME"))
    })
    @Embedded
    private FullName name;

    @Column(name = "SEX", nullable = false)
    private Sex sex;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Getter
    @Column(name = "COUPLE_ID")
    private Long coupleId;

    @Getter
    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CoupleRole role;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean activated;

    public void deactivate() {
        this.activated = false;
    }

    private MemberEntity(String email, String password, FullName name, Sex sex) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = CoupleRole.EMPTY;
        this.activated = true;
        this.sex = sex;
    }

    public static MemberEntity create(String email, String password, FullName name, Sex sex) {
        return new MemberEntity(email, password, name, sex);
    }

    public boolean hasRelactionship() {
        return Objects.nonNull(this.coupleId);
    }

    public void relatedAs(Long coupleId, CoupleRole role) {
        this.coupleId = coupleId;
        this.role = role;
    }

}
