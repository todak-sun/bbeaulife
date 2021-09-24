package com.todak.bbeaulife.application.member.repository;

import com.todak.bbeaulife.application.member.Member;
import com.todak.bbeaulife.config.WithContainer;
import com.todak.bbeaulife.entities.MemberEntity;
import com.todak.bbeaulife.type.FullName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class MemberRepositoryTest extends WithContainer {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void is_bean() {
        assertNotNull(memberRepository);
    }

    @DisplayName("memberEntity를 성공적으로 저장하는 테스트")
    @Test
    public void saveTest() {
        // given
        String email = "email@email.com";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        FullName name = FullName.called(firstName, lastName);

        MemberEntity newMember = MemberEntity.create(email, password, name);

        // when
        MemberEntity savedMember = memberRepository.save(newMember);

        // then
        assertNotNull(savedMember, "repository를 통해 저장된 memberEntity가 반환된다.");
        assertNotNull(savedMember.getId(), "저장된 후, member는 id를 가진다.");
    }

    @DisplayName("member를 찾아오는 테스트")
    @Test
    public void find_member_by_id_test() {
        // given
        String email = "email@email.com";
        String password = "password";
        String firstName = "firstName";
        String lastName = "lastName";
        FullName name = FullName.called(firstName, lastName);

        MemberEntity newMember = MemberEntity.create(email, password, name);

        // when
        MemberEntity savedMember = memberRepository.save(newMember);
        Optional<Member> optionalMember = memberRepository.findMemberById(savedMember.getId());

        // then
        assertTrue(optionalMember.isPresent(), "저장된 entity를 member로 받아올 수 있다.");
        Member member = optionalMember.get();
        assertEquals(email, member.getEmail(), "entity의 email과 같다");
        assertEquals(savedMember.getId(), member.getId(), "entity의 id와 같다");
    }


}