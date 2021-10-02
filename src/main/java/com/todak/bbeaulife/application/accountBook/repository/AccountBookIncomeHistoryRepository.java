package com.todak.bbeaulife.application.accountBook.repository;

import com.todak.bbeaulife.application.accountBook.AccountBookIncomeHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookIncomeHistoryRepository extends JpaRepository<AccountBookIncomeHistoryEntity, Long> {
}
