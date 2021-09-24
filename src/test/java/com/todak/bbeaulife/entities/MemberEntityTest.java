package com.todak.bbeaulife.entities;

import com.todak.bbeaulife.type.FullName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MemberEntityTest {

    @DisplayName("MemberEntity를 최초로 생성했을 때 테스트")
    @Test
    public void createTest() {
        String email = "email@email.com";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        FullName name = FullName.called(firstName, lastName);

        MemberEntity memberEntity = MemberEntity.create(email, password, name);

        assertNotNull(memberEntity, "memberEntity는 null이 아니다");
        assertNull(memberEntity.getId(), "memberEntity를 최초 생성했을 때, ID는 존재하지 않는다.");
    }

}