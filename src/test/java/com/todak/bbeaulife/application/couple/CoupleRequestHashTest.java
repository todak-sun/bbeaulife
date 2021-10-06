package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.couple.exception.CoupleMissMatchException;
import com.todak.bbeaulife.type.CoupleRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class CoupleRequestHashTest {

    @DisplayName("정상적으로 CoupleRequestHash를 생성하는 테스트")
    @Test
    void createTest() {
        long requesterId = 1L;
        long requesteeId = 2L;
        CoupleRole role = CoupleRole.WIFE;

        CoupleRequestHash coupleRequestHash = CoupleRequestHash.create(requesterId, requesteeId, role);
        assertNotNull(coupleRequestHash, "null이 아니다.");

        assertEquals(requesterId, coupleRequestHash.getRequesterId(), "요청자가 동일하게 들어간다.");
        assertEquals(requesteeId, coupleRequestHash.getRequesteeId(), "피요청자가 동일하게 들어간다.");
        assertEquals(role, coupleRequestHash.getRequesterRole(), "역할이 동일하게 들어간다");
        assertEquals(Duration.ofMinutes(30L).getSeconds(), coupleRequestHash.getTimeout(), "30분의 TTL을 가진다.");
    }

    @DisplayName("요청자와 피요청자가 같은 경우, 에러를 반환한다.")
    @Test
    void createTest_Fail() {
        long requesterId = 1L;
        long requesteeId = 1L;
        CoupleMissMatchException coupleMissMatchException = assertThrows(CoupleMissMatchException.class,
                () -> {
                    CoupleRequestHash.create(requesterId, requesteeId, CoupleRole.HUSBAND);
                });

        assertNotNull(coupleMissMatchException.getMessage(), "message는 null이 아니다.");
        assertEquals(requesterId, coupleMissMatchException.getRequesterId(), "requestId가 동일하다.");
        assertEquals(requesteeId, coupleMissMatchException.getRequesteeId(), "requesteeId가 동일하다.");
    }

    @DisplayName("해쉬코드 값이 같은지 테스트")
    @Test
    void hashCodeTest() {

        CoupleRequestHash hash1 = CoupleRequestHash.create(1L, 2L, CoupleRole.EMPTY);
        CoupleRequestHash hash2 = CoupleRequestHash.create(1L, 2L, CoupleRole.EMPTY);

        assertEquals(hash1.hashCode(), hash2.hashCode(), "데이터가 같다면, 해쉬값도 동일하게 나온다.");
    }

}
