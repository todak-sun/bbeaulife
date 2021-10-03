package com.todak.bbeaulife.application.accountBook.repository;

import com.todak.bbeaulife.application.accountBook.entity.AccountBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBookEntity, Long>, AccountBookRepositoryCustom {

    Optional<AccountBookEntity> findByCoupleId(Long coupleId);

    boolean existsByIdAndCoupleId(Long accountBookId, Long coupleId);

}
