package com.todak.bbeaulife.application.member;

import com.todak.bbeaulife.application.member.exception.NotFoundMemberException;
import com.todak.bbeaulife.application.member.repository.MemberRepository;
import com.todak.bbeaulife.entities.MemberEntity;
import com.todak.bbeaulife.type.CoupleRole;
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
    public void relateMembers(Long requesterId, CoupleRole requesterRole, Long requesteeId) {
        MemberEntity requester = memberRepository.findById(requesterId)
                .orElseThrow(() -> new NotFoundMemberException(requesterId));
        requester.roleAs(requesterRole);

        MemberEntity requestee = memberRepository.findById(requesteeId)
                .orElseThrow(() -> new NotFoundMemberException(requesteeId));
        requestee.roleAs(requesterRole.getOpposite());
    }

}
