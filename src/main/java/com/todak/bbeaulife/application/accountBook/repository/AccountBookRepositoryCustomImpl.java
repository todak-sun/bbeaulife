package com.todak.bbeaulife.application.accountBook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todak.bbeaulife.application.accountBook.IncomeHistory;
import com.todak.bbeaulife.application.accountBook.QIncomeHistory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.todak.bbeaulife.application.accountBook.QAccountBookHistoryEntity.accountBookHistoryEntity;

@RequiredArgsConstructor
public class AccountBookRepositoryCustomImpl implements AccountBookRepositoryCustom {

    private final JPAQueryFactory qf;

    @Override
    public List<IncomeHistory> findAllIncomeHistoriesByAccountBookId(Long accountBookId) {
        return qf.select(new QIncomeHistory(
                accountBookHistoryEntity.id,
                accountBookHistoryEntity.amount,
                accountBookHistoryEntity.incomeCategory,
                accountBookHistoryEntity.incomeCategoryLevel1,
                accountBookHistoryEntity.description,
                accountBookHistoryEntity.occuredDateTime,
                accountBookHistoryEntity.writtenBy
        ))
                .from(accountBookHistoryEntity)
                .where(accountBookHistoryEntity.accountBook.id.eq(accountBookId))
                .orderBy(accountBookHistoryEntity.occuredDateTime.desc())
                .fetch();
    }
}
