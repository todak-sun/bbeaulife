package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.entities.AccountBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBookEntity, Long> {

    Optional<AccountBookEntity> findByCoupleId(Long coupleId);


}
