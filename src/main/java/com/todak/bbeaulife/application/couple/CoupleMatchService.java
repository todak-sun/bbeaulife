package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.couple.exception.CoupleMissMatchException;
import com.todak.bbeaulife.application.couple.repository.CoupleRepository;
import com.todak.bbeaulife.application.couple.repository.CoupleRequestRedisRepository;
import com.todak.bbeaulife.application.member.Member;
import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CoupleMatchService {

    private final CoupleRepository coupleRepository;
    private final CoupleRequestRedisRepository coupleRequestRedisRepository;
    private final MemberApplicatoinService memberApplicatoinService;


    public Long suggestRelation(Long requesterId, CoupleRole myRole, Long requesteeId) {

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

        CoupleRequestHash save = coupleRequestRedisRepository.save(CoupleRequestHash.create(requester.getId(), requestee.getId(), myRole));

        return save.getTimeout();
    }

    @Transactional
    public void acceptRelation(Long requesteeId, Long requesterId) {
        // TODO : acceptRelation 구현
        CoupleRequestHash founded = coupleRequestRedisRepository.findById(requesteeId)
                .orElseThrow(() -> new RuntimeException("수락 기간이 지나감"));

        if (!founded.getRequesteeId().equals(requesterId)) {
            throw new RuntimeException("커플 신청자가 신청한 사람이 내가 아님");
        }


//        if(coupleRepository.existsByHusbandIdAndWifeId())

//        memberApplicatoinService.relateMembers(
//                founded.getRequesterId(),
//                founded.getRole(),
//                founded.getRequesteeId());

    }

}
