package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.entities.CoupleMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoupleMemberRepository extends JpaRepository<CoupleMemberEntity, Long> {
}
