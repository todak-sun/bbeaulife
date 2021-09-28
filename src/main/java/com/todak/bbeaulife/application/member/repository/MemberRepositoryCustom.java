package com.todak.bbeaulife.application.member.repository;

import com.todak.bbeaulife.application.member.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> findMemberById(Long id);

    Optional<Member> findMemberByCoupleIdAndPartnerId(Long memberId, Long coupleId);

}
