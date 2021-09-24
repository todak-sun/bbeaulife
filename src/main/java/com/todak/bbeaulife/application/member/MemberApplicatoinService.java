package com.todak.bbeaulife.application.member;

import com.todak.bbeaulife.application.member.exception.DuplicateEmailException;
import com.todak.bbeaulife.application.member.exception.NotFoundMemberException;
import com.todak.bbeaulife.application.member.repository.MemberRepository;
import com.todak.bbeaulife.entities.MemberEntity;
import com.todak.bbeaulife.type.CoupleRole;
import com.todak.bbeaulife.type.FullName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberApplicatoinService {

    private final MemberRepository memberRepository;

    public Member getMemberById(Long memberId) {
        return memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
    }

    @Transactional
    public Member createMember(String email, String password, String firstName, String lastName) {

        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }

        MemberEntity newMember = MemberEntity.create(email, password, FullName.called(firstName, lastName));

        MemberEntity savedMember = memberRepository.save(newMember);

        return MemberResolver.resolve(savedMember);
    }

    @Transactional
    public Member promoteAsCouple(Long memberId, Long coulpleId, CoupleRole coupleRole) {
        MemberEntity foundedMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
        foundedMember.relatedAs(coulpleId, coupleRole);

        return MemberResolver.resolve(foundedMember);
    }

}
