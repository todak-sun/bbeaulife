package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.member.Member;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.Getter;

public class Couple {

    @Getter
    private final Long id;
    @Getter
    private final Member husband;
    @Getter
    private final Member wife;

    private Couple(Long id, Member husband, Member wife) {
        this.id = id;
        this.husband = husband;
        this.wife = wife;
    }

    public static Couple create(Long id, Member a, Member b) {
        if (!a.hasPartner() || !b.hasPartner()) {
            throw new RuntimeException("둘 중 하나가 파트너가 없다.");
        }
        if (a.getRole() == b.getRole()) {
            throw new RuntimeException("둘다 같은 역할을 수행한다.");
        }
        if (CoupleRole.HUSBAND == a.getRole()) {
            return new Couple(id, a, b);
        } else {
            return new Couple(id, b, a);
        }
    }


}
