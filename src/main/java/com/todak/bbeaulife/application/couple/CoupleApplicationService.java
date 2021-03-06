package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.couple.exception.CoupleMissMatchException;
import com.todak.bbeaulife.application.couple.repository.CoupleRepository;
import com.todak.bbeaulife.application.couple.repository.CoupleRequestRedisRepository;
import com.todak.bbeaulife.application.member.Member;
import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import com.todak.bbeaulife.entities.CoupleEntity;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CoupleApplicationService {

    private final CoupleRepository coupleRepository;
    private final CoupleRequestRedisRepository coupleRequestRedisRepository;
    private final MemberApplicatoinService memberApplicatoinService;

    public Couple fetchCouple(Long memberId) {

        Member memberA = memberApplicatoinService.getMemberById(memberId);
        if (!memberA.hasPartner()) {
            throw new RuntimeException("커플이 아님");
        }
        CoupleEntity coupleEntity = coupleRepository.findById(memberA.getCoupleId())
                .orElseThrow(() -> new RuntimeException("이건 큰 문제다. 정합성이 안맞는다."));

        Member memberB = memberApplicatoinService.getPartner(memberA.getId(), coupleEntity.getId());
        return Couple.create(coupleEntity.getId(), memberA, memberB);
    }

    public Long suggestRelation(Long requesterId, CoupleRole requesterRole, Long requesteeId) {
        if (coupleRequestRedisRepository.existsById(requesterId)) {
            throw new CoupleMissMatchException(requesterId, requesteeId, "이미 진행중인 커플 신청이 있습니다.");
        }

        if (coupleRequestRedisRepository.existsById(requesteeId)) {
            throw new CoupleMissMatchException(requesterId, requesteeId, "상대방이 이미 커플 신청중에 있습니다.");
        }

        Member requester = memberApplicatoinService.getMemberById(requesterId);
        if (requester.hasPartner()) {
            throw new CoupleMissMatchException(requesterId, requesteeId, "이미 커플입니다.");
        }

        Member requestee = memberApplicatoinService.getMemberById(requesteeId);
        if (requestee.hasPartner()) {
            throw new CoupleMissMatchException(requesterId, requesteeId, "상대방이 커플입니다.");
        }

        CoupleRequestHash save = coupleRequestRedisRepository.save(
                CoupleRequestHash.create(requester.getId(), requestee.getId(), requesterRole)
        );
        // 수락 가능 기간 반환.
        return save.getTimeout();
    }

    @Transactional
    public Couple acceptRelation(Long requesteeId, Long requesterId) {

        CoupleRequestHash founded = coupleRequestRedisRepository.findById(requesterId)
                .orElseThrow(() ->
                        new CoupleMissMatchException(requesterId, requesteeId,
                                "수락 기간이 지났거나, 진행중인 커플 신청이 없습니다."));

        if (!founded.getRequesteeId().equals(requesteeId)) {
            throw new RuntimeException("커플 신청자가 신청한 사람이 내가 아님");
        }

        CoupleEntity newCouple = coupleRepository.save(CoupleEntity.create());

        Member requester = memberApplicatoinService.promoteAsCouple(
                founded.getRequesterId(),
                newCouple.getId(),
                founded.getRequesterRole());

        Member requestee = memberApplicatoinService.promoteAsCouple(
                founded.getRequesteeId(),
                newCouple.getId(),
                founded.getRequesteeRole());

        return Couple.create(newCouple.getId(), requester, requestee);
    }

}
