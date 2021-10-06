package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.application.accountBook.entity.AccountBookEntity;
import com.todak.bbeaulife.application.accountBook.repository.AccountBookRepository;
import com.todak.bbeaulife.application.couple.Couple;
import com.todak.bbeaulife.application.couple.CoupleApplicationService;
import com.todak.bbeaulife.application.couple.repository.CoupleRepository;
import com.todak.bbeaulife.application.member.Member;
import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import com.todak.bbeaulife.application.member.repository.MemberRepository;
import com.todak.bbeaulife.config.WithContainer;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class AccountBookApplicationServiceTestUnion extends WithContainer {

    @Autowired
    AccountBookApplicationService accountBookApplicationService;

    @Autowired
    CoupleApplicationService coupleApplicationService;

    @Autowired
    MemberApplicatoinService memberApplicatoinService;

    @Autowired
    AccountBookRepository accountBookRepository;

    @Autowired
    CoupleRepository coupleRepository;

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void afterEach() {
        accountBookRepository.deleteAll();
        coupleRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("스프링 빈으로 관리되고 있다.")
    @Test
    void is_bean() {
        assertNotNull(accountBookApplicationService);
    }

    @DisplayName("가계부를 성공적으로 생성하는 테스트")
    @Test
    void create_account_book_success() {
        //given
        Couple couple = createCouple();
        String accountBookName = "가계부";

        //when
        AccountBook newAccountBook = accountBookApplicationService.create(accountBookName, couple.getWife().getId(), couple.getId());

        //then
        assertNotNull(newAccountBook, "생성한 가계부가 존재한다.");
        assertNotNull(newAccountBook.getId(), "가계부의 식별값이 존재한다.");
        assertEquals(couple.getId(), newAccountBook.getCoupleId(), "생성시 입력한 coupleId가 동일하게 들어가 있다.");
        assertEquals(accountBookName, newAccountBook.getName(), "생성시 사용한 이름으로 가계부 이름이 정해진다.");
    }

    @DisplayName("수입 항목을 기입하는 테스트")
    @Test
    void write_income_success() {
        //given
        Couple couple = createCouple();
        String accountBookName = "가계부";
        AccountBook accountBook = accountBookApplicationService.create(accountBookName, couple.getHusband().getId(), couple.getId());
        IncomeDto dto = new IncomeDto(10000L,
                IncomeCategory.ETC,
                IncomeCategoryLevel1.ETC,
                "길가다 돈을 주움",
                LocalDate.of(2021, 9, 27));

        //when
        accountBookApplicationService.writeIncome(
                accountBook.getId(),
                dto,
                couple.getHusband().getId()
        );

        //then
        Optional<AccountBookEntity> optional = accountBookRepository.findById(accountBook.getId());
        assertTrue(optional.isPresent(), "accountBook이 존재한다");
        AccountBookEntity accountBookEntity = optional.get();

        List<IncomeHistory> incomeHistories = accountBookApplicationService.fetchIncomeHistories(accountBookEntity.getId(), accountBookEntity.getCoupleId());
        assertFalse(incomeHistories.isEmpty(), "위에서 입력한 내역이 존재한다.");

        incomeHistories.forEach(income -> log.info("income : {}", income));
    }

    @DisplayName("수입 항목을 여러개 입력하는 테스트")
    @Test
    void write_income_list_success() {
        //given
        Couple couple = createCouple();
        String accountBookName = "가계부";
        AccountBook accountBook = accountBookApplicationService.create(accountBookName, couple.getHusband().getId(), couple.getId());

        List<IncomeDto> dtos = IntStream.range(0, 100)
                .mapToObj((i) -> new IncomeDto((i + 1) * 100L,
                        IncomeCategory.ETC,
                        IncomeCategoryLevel1.ETC,
                        "description " + i,
                        LocalDate.now().plusDays(i)))
                .collect(Collectors.toList());

        //when
        accountBookApplicationService.writeIncome(accountBook.getId(), dtos, couple.getHusband().getId());

        //then
        Optional<AccountBookEntity> optional = accountBookRepository.findById(accountBook.getId());
        assertTrue(optional.isPresent(), "accountBook이 존재한다");
        AccountBookEntity accountBookEntity = optional.get();

        List<IncomeHistory> incomeHistories = accountBookApplicationService.fetchIncomeHistories(accountBookEntity.getId(), accountBookEntity.getCoupleId());
        assertFalse(incomeHistories.isEmpty(), "위에서 입력한 내역이 존재한다.");

        incomeHistories.forEach(income -> log.info("income : {}", income));
    }

    private Couple createCouple() {
        Member wife = memberApplicatoinService.createMember("wife@email.com", "password", "first", "last");
        Member husband = memberApplicatoinService.createMember("husband@email.com", "password", "first", "last");

        coupleApplicationService.suggestRelation(wife.getId(), CoupleRole.WIFE, husband.getId());
        return coupleApplicationService.acceptRelation(husband.getId(), wife.getId());
    }


}
