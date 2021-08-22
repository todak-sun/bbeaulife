package com.todak.bbeaulife.repository;

import com.todak.bbeaulife.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
