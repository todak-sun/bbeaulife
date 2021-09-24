package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.member.Member;
import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import com.todak.bbeaulife.config.WithContainer;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class CoupleApplicationServiceUnionTest extends WithContainer {

    @Autowired
    CoupleApplicationService coupleApplicationService;
    @Autowired
    MemberApplicatoinService memberApplicatoinService;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void success_and_accept() throws JsonProcessingException {
        String requesterEmail = "requester@email.com";
        String requesterPassword = "requester-password";
        String requesterFirstName = "requesterFirst";
        String requesterLastName = "requesterLast";

        String requesteeEmail = "requestee@email.com";
        String requesteePassword = "requestee-password";
        String requesteeFirstName = "requesteeFirst";
        String requesteeLastName = "requesteeLast";

        Member requester = memberApplicatoinService.createMember(requesterEmail, requesterPassword, requesterFirstName, requesterLastName);
        Member requestee = memberApplicatoinService.createMember(requesteeEmail, requesteePassword, requesteeFirstName, requesteeLastName);

        Long timeOut = coupleApplicationService.suggestRelation(requester.getId(), CoupleRole.WIFE, requestee.getId());

        Couple couple = coupleApplicationService.acceptRelation(requestee.getId(), requester.getId());

        assertNotNull(timeOut);
        assertNotNull(couple);
        assertNotNull(couple.getId());

        assertNotNull(couple.getWife());
        assertEquals(CoupleRole.WIFE, couple.getWife().getRole());
        assertEquals(requester.getId(), couple.getWife().getId());
        assertEquals(requester.getEmail(), couple.getWife().getEmail());


        assertNotNull(couple.getHusband());
        assertEquals(CoupleRole.HUSBAND, couple.getHusband().getRole());
        assertEquals(requestee.getId(), couple.getHusband().getId());
        assertEquals(requestee.getEmail(), couple.getHusband().getEmail());

        String s = objectMapper.writeValueAsString(couple);
        log.info("s : {}", s);
    }

}
