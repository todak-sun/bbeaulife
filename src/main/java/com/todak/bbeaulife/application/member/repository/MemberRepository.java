package com.todak.bbeaulife.application.member.repository;

import com.todak.bbeaulife.entities.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberRepositoryCustom {

    boolean existsByEmail(String email);

    boolean existsByIdAndCoupleId(Long memberId, Long coupleId);

}
