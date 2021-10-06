package com.todak.bbeaulife.application.accountBook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todak.bbeaulife.application.accountBook.IncomeHistory;
import com.todak.bbeaulife.application.accountBook.QIncomeHistory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.todak.bbeaulife.application.accountBook.entity.QIncomeHistoryEntity.incomeHistoryEntity;


@RequiredArgsConstructor
public class AccountBookRepositoryCustomImpl implements AccountBookRepositoryCustom {

    private final JPAQueryFactory qf;

    @Override
    public List<IncomeHistory> findAllIncomeHistoriesByAccountBookId(Long accountBookId) {
        return qf.select(new QIncomeHistory(
                incomeHistoryEntity.id,
                incomeHistoryEntity.amount,
                incomeHistoryEntity.category,
                incomeHistoryEntity.categoryLevel1,
                incomeHistoryEntity.description,
                incomeHistoryEntity.occuredDateTime,
                incomeHistoryEntity.writtenBy
        ))
                .from(incomeHistoryEntity)
                .where(incomeHistoryEntity.accountBook.id.eq(accountBookId))
                .orderBy(incomeHistoryEntity.occuredDateTime.desc())
                .fetch();
    }
}
