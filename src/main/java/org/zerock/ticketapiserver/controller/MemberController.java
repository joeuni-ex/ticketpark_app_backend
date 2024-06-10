package org.zerock.ticketapiserver.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.ticketapiserver.dto.CheckEmailDTO;
import org.zerock.ticketapiserver.dto.MemberDTO;
import org.zerock.ticketapiserver.service.MemberService;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    //가입하기
    @PostMapping("/")
    public Map<String,String> register ( MemberDTO memberDTO) {

        log.info("member register--------------------" + memberDTO);

        memberService.register(memberDTO);

        return Map.of("RESULT", "SUCCESS");

    }

    //이메일 중복 확인 (Check email duplicates)
    @GetMapping("/check-email")
    public Map<String, String> checkEmail(CheckEmailDTO checkEmailDTO) {
        boolean isDuplicate = memberService.emailDuplicates(checkEmailDTO);
        return Map.of("RESULT", isDuplicate ? "DUPLICATE" : "AVAILABLE");
    }



}
