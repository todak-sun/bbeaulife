package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.couple.exception.CoupleMissMatchException;
import com.todak.bbeaulife.application.couple.exception.NotFoundMemberException;
import com.todak.bbeaulife.entities.MemberEntity;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoupleMatchService {

    private final MemberRepository memberRepository;

    private final CoupleRequestRedisRepository coupleRequestRedisRepository;


    public Long suggestRelation(Long myId, CoupleRole myRole, Long partnerId) {

        if (coupleRequestRedisRepository.existsById(myId)) {
            throw new CoupleMissMatchException(myId, partnerId, "이미 진행중인 커플 신청이 있습니다.");
        }

        if (coupleRequestRedisRepository.existsById(partnerId)) {
            throw new CoupleMissMatchException(myId, partnerId, "상대방이 이미 커플 신청중에 있습니다.");
        }

        MemberEntity me = memberRepository.findById(myId)
                .orElseThrow(() -> new NotFoundMemberException(myId));

        if (me.hasPartner()) {
            throw new CoupleMissMatchException(myId, partnerId, "이미 커플입니다.");
        }

        MemberEntity partner = memberRepository.findById(partnerId)
                .orElseThrow(() -> new NotFoundMemberException(partnerId));

        if (partner.hasPartner()) {
            throw new CoupleMissMatchException(myId, partnerId, "상대방이 커플입니다.");
        }

        CoupleRequestHash save = coupleRequestRedisRepository.save(CoupleRequestHash.create(me.getId(), partner.getId(), myRole));

        return save.getTimeout();
    }

    public void acceptRelation(Long partnerId, Long myId) {

        CoupleRequestHash founded = coupleRequestRedisRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("수락 기간이 지나감"));

        if (!founded.getRequesteeId().equals(myId)) {
            throw new RuntimeException("커플 신청자가 신청한 사람이 내가 아님");
        }

        memberRepository.findById(partnerId).orElseThrow(() -> new RuntimeException("파트너가 존재하지않음"));


    }

}
