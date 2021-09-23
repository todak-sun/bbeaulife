package com.todak.bbeaulife.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MemberEntityTest {

    @DisplayName("MemberEntity를 최초로 생성했을 때 테스트")
    @Test
    public void createTest() {
        String email = "email@email.com";
        String password = "password";

        MemberEntity memberEntity = MemberEntity.create(email, password);
        assertNotNull(memberEntity, "memberEntity는 null이 아니다");
        assertNull(memberEntity.getId(), "memberEntity를 최초 생성했을 때, ID는 존재하지 않는다.");
        assertFalse(memberEntity.hasPartner(), "memberEntity를 최초 생성했을 때, 파트너는 존재하지 않는다.");
    }

}