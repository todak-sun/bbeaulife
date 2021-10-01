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

    public boolean exsistById(Long memberId) {
        return memberRepository.existsByIdAndActivatedTrue(memberId);
    }

    public boolean isMatchedCouple(Long memberId, Long coupleId) {
        return memberRepository.existsByIdAndCoupleId(memberId, coupleId);
    }

    public Member getMemberById(Long memberId) {
        return memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
    }

    public Member getPartner(Long memberId, Long coupleId) {
        return memberRepository.findMemberByCoupleIdAndPartnerId(memberId, coupleId)
                .orElseThrow(() -> new RuntimeException("파트너가 없음"));
    }

    @Transactional
    public void withdrawal(Long memberId) {
        MemberEntity memberEntity = memberRepository.findByIdAndActivatedTrue(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
        memberEntity.deactivate();
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

        if (foundedMember.hasRelactionship()) {
            throw new RuntimeException("이미 커플입니다.");
        }

        foundedMember.relatedAs(coulpleId, coupleRole);

        return MemberResolver.resolve(foundedMember);
    }

}
