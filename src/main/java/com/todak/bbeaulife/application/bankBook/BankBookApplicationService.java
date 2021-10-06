package com.todak.bbeaulife.application.bankBook;

import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import com.todak.bbeaulife.application.member.exception.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankBookApplicationService {

    private final BankBookRepository bankBookRepository;

    private final MemberApplicatoinService memberApplicatoinService;


    public BankBook enrollBankBook(Long ownerId, Bank bank, String accountNumber, String bankBookNickName) {

        if (!memberApplicatoinService.exsistById(ownerId)) {
            throw new NotFoundMemberException(ownerId);
        }

        BankBookEntity savedBankBook = bankBookRepository.save(BankBookEntity.create(bank, accountNumber, ownerId, bankBookNickName));

        return BankBook.create(
                savedBankBook.getId(),
                savedBankBook.getBank(),
                savedBankBook.getAccountNumber(),
                savedBankBook.getNickName());
    }

}
