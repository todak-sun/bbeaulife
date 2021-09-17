package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.entities.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {


}
