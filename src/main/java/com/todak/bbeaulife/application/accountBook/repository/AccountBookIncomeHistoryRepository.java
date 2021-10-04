package com.todak.bbeaulife.application.accountBook.repository;

import com.todak.bbeaulife.application.accountBook.entity.IncomeHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookIncomeHistoryRepository extends JpaRepository<IncomeHistoryEntity, Long> {
}
