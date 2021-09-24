package com.todak.bbeaulife.application.couple.repository;

import com.todak.bbeaulife.entities.CoupleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoupleRepository extends JpaRepository<CoupleEntity, Long> {

//    boolean existsByHusbandIdAndWifeId(Long husbandId, Long wifeId);

}
