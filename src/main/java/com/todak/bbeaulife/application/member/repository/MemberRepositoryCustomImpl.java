package com.todak.bbeaulife.application.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todak.bbeaulife.application.member.Member;
import com.todak.bbeaulife.application.member.QMember;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.todak.bbeaulife.entities.QMemberEntity.memberEntity;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory qf;

    @Override
    public Optional<Member> findMemberById(Long id) {
        Member member = qf.select(member())
                .from(memberEntity)
                .where(memberEntity.id.eq(id), activeMember())
                .fetchOne();
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findMemberByCoupleIdAndPartnerId(Long memberId, Long coupleId) {
        Member member = qf.select(member())
                .from(memberEntity)
                .where(
                        memberEntity.coupleId.eq(coupleId),
                        memberEntity.id.ne(memberId),
                        activeMember()
                ).fetchOne();
        return Optional.ofNullable(member);
    }

    private QMember member() {
        return new QMember(memberEntity.id,
                memberEntity.email,
                memberEntity.name,
                memberEntity.role,
                memberEntity.coupleId);
    }

    private BooleanExpression activeMember() {
        return memberEntity.activated.isTrue();
    }

}
