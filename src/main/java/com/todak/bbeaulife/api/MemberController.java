package com.todak.bbeaulife.api;

import com.todak.bbeaulife.api.request.member.MemberCreate;
import com.todak.bbeaulife.api.response.ErrorDto;
import com.todak.bbeaulife.application.member.MemberApplicatoinService;
import com.todak.bbeaulife.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberApplicatoinService memberApplicatoinService;

    @PostMapping
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberCreate member, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors.getFieldErrors());
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
