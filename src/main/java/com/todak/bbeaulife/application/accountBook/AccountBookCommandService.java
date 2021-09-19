package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.application.accountBook.exception.AlreadyExistAccountBookException;
import com.todak.bbeaulife.application.couple.Couple;
import com.todak.bbeaulife.entities.AccountBookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountBookCommandService {

    private final AccountBookRepository accountBookRepository;

    public AccountBook create(String name, Couple couple) {

        if (accountBookRepository.findByCoupleId(couple.getId()).isPresent()) {
            throw new AlreadyExistAccountBookException();
        }

        AccountBookEntity newAccountBook = accountBookRepository.save(AccountBookEntity.create(name, couple.getId()));

        return new AccountBook(
                newAccountBook.getId(),
                newAccountBook.getName(),
                couple);
    }

}
