package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.application.accountBook.entity.AccountBookEntity;
import com.todak.bbeaulife.application.accountBook.entity.AccountBookIncomeHistoryEntity;
import com.todak.bbeaulife.application.accountBook.exception.AlreadyExistAccountBookException;
import com.todak.bbeaulife.application.accountBook.repository.AccountBookHistoryRepository;
import com.todak.bbeaulife.application.accountBook.repository.AccountBookIncomeHistoryRepository;
import com.todak.bbeaulife.application.accountBook.repository.AccountBookRepository;
import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountBookApplicationService {

    private final AccountBookRepository accountBookRepository;
    private final AccountBookHistoryRepository accountBookHistoryRepository;
    private final AccountBookIncomeHistoryRepository accountBookIncomeHistoryRepository;
    private final MemberApplicatoinService memberApplicatoinService;

    @Transactional
    public AccountBook create(String name, Long memberId, Long coupleId) {

        if (accountBookRepository.findByCoupleId(coupleId).isPresent()) {
            throw new AlreadyExistAccountBookException();
        }

        if (!memberApplicatoinService.isMatchedCouple(memberId, coupleId)) {
            throw new RuntimeException("커플이 아닙니다.");
        }

        AccountBookEntity newAccountBook = accountBookRepository.save(AccountBookEntity.create(name, coupleId));

        return new AccountBook(
                newAccountBook.getId(),
                newAccountBook.getName(),
                coupleId);
    }

    @Transactional
    public void writeIncome(Long accountBookId, IncomeDto dto, Long writerId) {

        AccountBookEntity accountBook = accountBookRepository.findById(accountBookId)
                .orElseThrow(() -> new RuntimeException("accountBook이 존재하지 않음"));

        if (!memberApplicatoinService.isMatchedCouple(writerId, accountBook.getCoupleId())) {
            throw new RuntimeException("커플이 아닙니다.");
        }

        accountBook.writeIncome(
                dto.amount(),
                dto.incomeCategory(),
                dto.incomeCategoryLevel1(),
                dto.description(),
                dto.occuredDateTime(),
                writerId
        );
    }

    @Transactional
    public void writeIncome(Long accountBookId, List<IncomeDto> dtos, Long writerId) {

        AccountBookEntity accountBook = accountBookRepository.findById(accountBookId)
                .orElseThrow(() -> new RuntimeException("accountBook이 존재하지 않음"));

        if (!memberApplicatoinService.isMatchedCouple(writerId, accountBook.getCoupleId())) {
            throw new RuntimeException("커플이 아닙니다.");
        }

        for (IncomeDto dto : dtos) {
            accountBook.writeIncome(
                    dto.amount(),
                    dto.incomeCategory(),
                    dto.incomeCategoryLevel1(),
                    dto.description(),
                    dto.occuredDateTime(),
                    writerId);
        }
    }

    @Transactional
    public void modifyIncomeHistory(Long accountBookHistoryId, IncomeDto dto, Long writerId) {
        AccountBookIncomeHistoryEntity incomeHistory = accountBookIncomeHistoryRepository.findById(accountBookHistoryId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 내역"));

        incomeHistory
                .modify(dto.amount(),
                        dto.description(),
                        dto.incomeCategory(),
                        dto.incomeCategoryLevel1(),
                        dto.occuredDateTime(),
                        writerId);
    }

    @Transactional
    public void deleteHistory(Long accountBookHistoryId) {
        accountBookHistoryRepository.deleteById(accountBookHistoryId);
    }

    @Transactional
    public List<IncomeHistory> fetchIncomeHistories(Long accountBookId, Long coupleId) {
        if (!accountBookRepository.existsByIdAndCoupleId(accountBookId, coupleId)) {
            throw new RuntimeException("가계부가 존재하지 않거나, 커플이 소유한 가계부가 아님");
        }

        return accountBookRepository.findAllIncomeHistoriesByAccountBookId(accountBookId);
    }


}
