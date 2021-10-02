package com.todak.bbeaulife.application.accountBook.repository;

import com.todak.bbeaulife.application.accountBook.AccountBookHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookHistoryRepository extends JpaRepository<AccountBookHistoryEntity, Long> {


}
