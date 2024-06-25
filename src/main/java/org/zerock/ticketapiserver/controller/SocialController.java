package org.zerock.ticketapiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.ticketapiserver.dto.MemberDTO;
import org.zerock.ticketapiserver.service.MemberService;
import org.zerock.ticketapiserver.util.JWTUtil;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    @GetMapping("/api/member/kakao")
    public Map<String, Object> getMemberFromKakao(String accessToken){

        log.info("accessToken : " + accessToken);

        MemberDTO memberDTO = memberService.getKakaoMember(accessToken);


        Map< String , Object> claims = memberDTO.getClaims();

        String jwtAccessToken = JWTUtil.generateToken(claims,10);//지금 당장 사용
        String jwtRefreshToken = JWTUtil.generateToken(claims,60*24);//

        claims.put("accessToken",jwtAccessToken);
        claims.put("refreshToken",jwtRefreshToken);

        return claims;
    }
}
