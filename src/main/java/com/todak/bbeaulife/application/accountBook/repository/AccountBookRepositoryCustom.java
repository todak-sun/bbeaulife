package com.todak.bbeaulife.application.accountBook.repository;

import com.todak.bbeaulife.application.accountBook.IncomeHistory;

import java.util.List;

public interface AccountBookRepositoryCustom {

    List<IncomeHistory> findAllIncomeHistoriesByAccountBookId(Long accountBookId);

}
