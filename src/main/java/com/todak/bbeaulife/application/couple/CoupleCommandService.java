package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.entities.MemberEntity;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoupleCommandService {

    private final MemberRepository memberRepository;


    public Couple relate(Long myId, CoupleRole myRole, Long coupleId, Long partnerId) {

        MemberEntity partner = memberRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 파트너"));
        MemberEntity me = memberRepository.getById(myId);



    }

}
