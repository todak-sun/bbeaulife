package com.todak.bbeaulife.application.bankBook;

import com.todak.bbeaulife.entities.BankBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankBookRepository extends JpaRepository<BankBookEntity, Long> {
}
