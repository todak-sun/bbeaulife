package com.todak.bbeaulife.application.accountBook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todak.bbeaulife.application.accountBook.IncomeHistory;
import com.todak.bbeaulife.application.accountBook.QIncomeHistory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.todak.bbeaulife.application.accountBook.entity.QAccountBookIncomeHistoryEntity.accountBookIncomeHistoryEntity;


@RequiredArgsConstructor
public class AccountBookRepositoryCustomImpl implements AccountBookRepositoryCustom {

    private final JPAQueryFactory qf;

    @Override
    public List<IncomeHistory> findAllIncomeHistoriesByAccountBookId(Long accountBookId) {
        return qf.select(new QIncomeHistory(
                accountBookIncomeHistoryEntity.id,
                accountBookIncomeHistoryEntity.amount,
                accountBookIncomeHistoryEntity.incomeCategory,
                accountBookIncomeHistoryEntity.incomeCategoryLevel1,
                accountBookIncomeHistoryEntity.description,
                accountBookIncomeHistoryEntity.occuredDateTime,
                accountBookIncomeHistoryEntity.writtenBy
        ))
                .from(accountBookIncomeHistoryEntity)
                .where(accountBookIncomeHistoryEntity.accountBook.id.eq(accountBookId))
                .orderBy(accountBookIncomeHistoryEntity.occuredDateTime.desc())
                .fetch();
    }
}
