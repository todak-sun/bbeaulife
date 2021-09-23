package com.todak.bbeaulife.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoupleRoleTest {

    @DisplayName("남편은 아내를, 아내는 남편을 반대 의미로 반환한다.")
    @Test
    void get_opposite_test() {
        assertEquals(CoupleRole.HUSBAND, CoupleRole.WIFE.getOpposite());
        assertEquals(CoupleRole.WIFE, CoupleRole.HUSBAND.getOpposite());
    }

}