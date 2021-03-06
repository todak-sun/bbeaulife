package com.todak.bbeaulife.application.accountBook;

import com.todak.bbeaulife.application.accountBook.entity.AccountBookEntity;
import com.todak.bbeaulife.application.accountBook.repository.AccountBookRepository;
import com.todak.bbeaulife.application.couple.Couple;
import com.todak.bbeaulife.application.couple.CoupleApplicationService;
import com.todak.bbeaulife.application.couple.repository.CoupleRepository;
import com.todak.bbeaulife.application.member.Member;
import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import com.todak.bbeaulife.application.member.UncertificatedMember;
import com.todak.bbeaulife.application.member.repository.MemberRepository;
import com.todak.bbeaulife.application.member.repository.UncertificatedMemberRepository;
import com.todak.bbeaulife.config.WithContainer;
import com.todak.bbeaulife.type.CoupleRole;
import com.todak.bbeaulife.type.Sex;
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

    @Autowired
    UncertificatedMemberRepository uncertificatedMemberRepository;

    @AfterEach
    void afterEach() {
        accountBookRepository.deleteAll();
        coupleRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("????????? ????????? ???????????? ??????.")
    @Test
    void is_bean() {
        assertNotNull(accountBookApplicationService);
    }

    @DisplayName("???????????? ??????????????? ???????????? ?????????")
    @Test
    void create_account_book_success() {
        //given
        Couple couple = createCouple();
        String accountBookName = "?????????";

        //when
        AccountBook newAccountBook = accountBookApplicationService.create(accountBookName, couple.getWife().getId(), couple.getId());

        //then
        assertNotNull(newAccountBook, "????????? ???????????? ????????????.");
        assertNotNull(newAccountBook.getId(), "???????????? ???????????? ????????????.");
        assertEquals(couple.getId(), newAccountBook.getCoupleId(), "????????? ????????? coupleId??? ???????????? ????????? ??????.");
        assertEquals(accountBookName, newAccountBook.getName(), "????????? ????????? ???????????? ????????? ????????? ????????????.");
    }

    @DisplayName("?????? ????????? ???????????? ?????????")
    @Test
    void write_income_success() {
        //given
        Couple couple = createCouple();
        String accountBookName = "?????????";
        AccountBook accountBook = accountBookApplicationService.create(accountBookName, couple.getHusband().getId(), couple.getId());
        IncomeDto dto = new IncomeDto(10000L,
                IncomeCategory.ETC,
                IncomeCategoryLevel1.ETC,
                "????????? ?????? ??????",
                LocalDate.of(2021, 9, 27));

        //when
        accountBookApplicationService.writeIncome(
                accountBook.getId(),
                dto,
                couple.getHusband().getId()
        );

        //then
        Optional<AccountBookEntity> optional = accountBookRepository.findById(accountBook.getId());
        assertTrue(optional.isPresent(), "accountBook??? ????????????");
        AccountBookEntity accountBookEntity = optional.get();

        List<IncomeHistory> incomeHistories = accountBookApplicationService.fetchIncomeHistories(accountBookEntity.getId(), accountBookEntity.getCoupleId());
        assertFalse(incomeHistories.isEmpty(), "????????? ????????? ????????? ????????????.");

        incomeHistories.forEach(income -> log.info("income : {}", income));
    }

    @DisplayName("?????? ????????? ????????? ???????????? ?????????")
    @Test
    void write_income_list_success() {
        //given
        Couple couple = createCouple();
        String accountBookName = "?????????";
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
        assertTrue(optional.isPresent(), "accountBook??? ????????????");
        AccountBookEntity accountBookEntity = optional.get();

        List<IncomeHistory> incomeHistories = accountBookApplicationService.fetchIncomeHistories(accountBookEntity.getId(), accountBookEntity.getCoupleId());
        assertFalse(incomeHistories.isEmpty(), "????????? ????????? ????????? ????????????.");

        incomeHistories.forEach(income -> log.info("income : {}", income));
    }

    private Couple createCouple() {
        String wifeEmail = "wife@email.com";

        memberApplicatoinService.createMember(wifeEmail, "password", "first", "last", Sex.FEMALE);
        UncertificatedMember cachedWife = uncertificatedMemberRepository.findById(wifeEmail).get();
        Member wife = memberApplicatoinService.certificateMember(wifeEmail, cachedWife.getCirtificateCode());

        String husbandEmail = "husband@email.com";
        memberApplicatoinService.createMember(husbandEmail, "password", "first", "last", Sex.MALE);
        UncertificatedMember cachedHusband = uncertificatedMemberRepository.findById(husbandEmail).get();
        Member husband = memberApplicatoinService.certificateMember(husbandEmail, cachedHusband.getCirtificateCode());

        coupleApplicationService.suggestRelation(wife.getId(), CoupleRole.WIFE, husband.getId());
        return coupleApplicationService.acceptRelation(husband.getId(), wife.getId());
    }


}
