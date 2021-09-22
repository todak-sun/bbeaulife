package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.entities.MemberEntity;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoupleCommandService {

    private final MemberRepository memberRepository;

    private final CoupleRequestRedisRepository coupleRequestRedisRepository;


    public Long suggestRelation(Long myId, CoupleRole myRole, Long partnerId) {

        if (coupleRequestRedisRepository.existsById(myId)) {
            throw new IllegalArgumentException("이미 커플 신청 했음");
        }

        if (coupleRequestRedisRepository.existsById(partnerId)) {
            throw new IllegalArgumentException("파트너가 이미 커플 신청 했음");
        }

        MemberEntity me = memberRepository.findById(myId)
                .orElseThrow(() -> new RuntimeException("이미 탈퇴한 사용자거나 문제가 있는 사용자임"));

        MemberEntity partner = memberRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자에게 커플 신청을 함"));

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
