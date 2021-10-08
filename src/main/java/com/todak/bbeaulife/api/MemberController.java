package com.todak.bbeaulife.api;

import com.todak.bbeaulife.api.request.member.MemberCreate;
import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberApplicatoinService memberApplicatoinService;

    @PostMapping
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberCreate member, Errors errors) {
        if (errors.hasErrors()) {
            //TODO: 에러처리
        }
//        memberApplicatoinService.createMember(
//                member.getEmail(),
//                member.getPassword(),
//                member.getFirstName(),
//                member.getLastName(),
//                member.getSex());
        return null;
    }

}
