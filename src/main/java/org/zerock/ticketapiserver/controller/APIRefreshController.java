package org.zerock.ticketapiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.ticketapiserver.util.CustomJWTException;
import org.zerock.ticketapiserver.util.JWTUtil;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {

    @RequestMapping("/api/member/refresh")
    public Map<String, Object>  refresh(
            @RequestHeader("Authorization") String authHeader,
            String refreshToken
    ){
        if(refreshToken == null){
            throw new CustomJWTException("NUll_REFRESH");
        }
        if(authHeader == null || authHeader.length()<7){
            throw new CustomJWTException("INVALID STRING");
        }

        //Bearer xxx
        String accessToken = authHeader.substring(7);

        //AccessToken의 만료 여부 확인
        if(checkExpiredToken(accessToken) == false){
            return Map.of("accessToken", accessToken,"refreshToken",refreshToken);
        }
        //RefreshToken 검증
        Map<String , Object> claims = JWTUtil.validateToken(refreshToken);

        //새로운 AccessToken 생성
        String newAccessToken = JWTUtil.generateToken(claims, 10);

        //RefreshToken이 유효시간이 1시간 미만으로 남았다면 새로운 refreshToken 생성
        String newRefreshToken = checkTime((Integer)claims.get("exp")) == true ? JWTUtil.generateToken(claims, 60*24) : refreshToken;

        //결과적으로 새로 만들어지는 토큰들 return
        return Map.of("accessToken", newAccessToken,"refreshToken",newRefreshToken);
    }

    //시간이 1시간 미만으로 남았다면
    //refreshToken이 만료가 안됐으면 처리
    private boolean checkTime(Integer exp){

        //JWT exp를 날짜로 변환
        java.util.Date expDate = new java.util.Date((long) exp * (1000));

        //현재0 시간과의 차이 계산 - 밀리세컨즈
        long gap = expDate.getTime() - System.currentTimeMillis();

        //분 단위 계산
        long leftMin = gap / (1000 * 60);

        //1시간도 안남았는지...
        return leftMin < 60;

    }

    //토큰을 넣었으면 만료됐는지 여부를 체크함
    private boolean checkExpiredToken(String token){

        try{
            JWTUtil.validateToken(token); //만료 여부
        }catch (CustomJWTException ex){
            if(ex.getMessage().equals("Expired")){ //만료 메시지
                return true;
            }
        }

        return false;
    }

}
