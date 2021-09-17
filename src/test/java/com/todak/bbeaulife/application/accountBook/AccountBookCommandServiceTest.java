package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.application.accountBook.exception.AlreadyExistAccountBookException;
import com.todak.bbeaulife.application.couple.Couple;
import com.todak.bbeaulife.application.couple.Husband;
import com.todak.bbeaulife.application.couple.Wife;
import com.todak.bbeaulife.entities.AccountBookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AccountBookCommandServiceTest {

    @Mock
    AccountBookRepository accountBookRepository;

    AccountBookCommandService accountBookCommandService;

    @BeforeEach
    void setUp() {
        this.accountBookCommandService = new AccountBookCommandService(accountBookRepository);
    }

    @DisplayName("가계부를 가지고 있지 않은 유저가, 가계부를 성공적으로 만드는 테스트")
    @Test
    public void create_test() {
        //given
        String accountName = "나의 가계부";
        Long coupleId = 1L;
        Couple couple = new Couple(coupleId, new Husband(), new Wife());
        AccountBookEntity accountBookEntity = AccountBookEntity.create(accountName, coupleId);

        given(accountBookRepository.findByCoupleId(coupleId)).willReturn(Optional.empty());
        given(accountBookRepository.save(any())).willReturn(accountBookEntity);

        //when
        AccountBook accountBook = this.accountBookCommandService.create(accountName, couple);

        //then
        assertEquals(accountBook.getName(), accountName);
        assertEquals(accountBook.getCouple(), couple);
    }

    @DisplayName("가계부를 이미 가진 유저가, 가계부를 추가로 만들고자 하는 경우를 테스트")
    @Test
    public void create_fail_test() {
        //given
        given(accountBookRepository.findByCoupleId(any()))
                .willReturn(Optional.of(AccountBookEntity.create("name", 1L)));


        //when & then
        assertThrows(AlreadyExistAccountBookException.class,
                () -> this.accountBookCommandService.create("name", new Couple(1L, new Husband(), new Wife())));

        then(accountBookRepository).shouldHaveNoMoreInteractions();

    }


}