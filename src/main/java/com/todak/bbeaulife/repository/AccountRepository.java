package com.todak.bbeaulife.repository;

import com.todak.bbeaulife.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

}
