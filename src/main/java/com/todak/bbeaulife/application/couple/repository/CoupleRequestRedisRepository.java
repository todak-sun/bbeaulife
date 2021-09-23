package com.todak.bbeaulife.application.couple.repository;

import com.todak.bbeaulife.application.couple.CoupleRequestHash;
import org.springframework.data.repository.CrudRepository;

public interface CoupleRequestRedisRepository extends CrudRepository<CoupleRequestHash, Long> {

}
