package com.todak.bbeaulife.application.member;

import com.todak.bbeaulife.application.member.exception.DuplicateEmailException;
import com.todak.bbeaulife.application.member.exception.NotFoundMemberException;
import com.todak.bbeaulife.application.member.repository.MemberRepository;
import com.todak.bbeaulife.application.member.repository.UncertificatedMemberRepository;
import com.todak.bbeaulife.entities.MemberEntity;
import com.todak.bbeaulife.type.CoupleRole;
import com.todak.bbeaulife.type.FullName;
import com.todak.bbeaulife.type.Sex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberApplicatoinService {

    private final MemberRepository memberRepository;
    private final UncertificatedMemberRepository uncertificatedMemberRepository;

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
    public void withdraw(Long memberId) {
        MemberEntity memberEntity = memberRepository.findByIdAndActivatedTrue(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
        memberEntity.deactivate();
    }

    public Long createMember(String email, String password, String firstName, String lastName, Sex sex) {
        if (uncertificatedMemberRepository.existsById(email)) {
            uncertificatedMemberRepository.deleteById(email);
        }

        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }

        UncertificatedMember cachedMember = uncertificatedMemberRepository.save(UncertificatedMember.create(
                email,
                password,
                FullName.called(firstName, lastName),
                sex
        ));
        return cachedMember.getTimeout();
    }

    @Transactional
    public Member certificateMember(String email, String certificateCode) {
        UncertificatedMember cachedMember = uncertificatedMemberRepository
                .findById(email)
                .orElseThrow(() -> new RuntimeException("인증 시간이 만료되었습니다."));

        if (!cachedMember.isValidCode(certificateCode)) {
            throw new RuntimeException("인증코드가 다릅니다.");
        }

        MemberEntity memberEntity = memberRepository.save(MemberEntity.create(
                cachedMember.getEmail(),
                cachedMember.getPassword(),
                cachedMember.getName(),
                cachedMember.getSex()));

        return MemberResolver.resolve(memberEntity);
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
