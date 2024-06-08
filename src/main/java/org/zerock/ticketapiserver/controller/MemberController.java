package org.zerock.ticketapiserver.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.ticketapiserver.dto.MemberDTO;
import org.zerock.ticketapiserver.service.MemberService;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/")
    public Map<String,String> register ( MemberDTO memberDTO) {

        log.info("member register--------------------" + memberDTO);

        memberService.register(memberDTO);

        return Map.of("RESULT", "SUCCESS");

    }
}
