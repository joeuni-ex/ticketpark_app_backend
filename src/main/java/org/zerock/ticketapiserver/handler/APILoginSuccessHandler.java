package org.zerock.ticketapiserver.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.ticketapiserver.dto.MemberDTO;
import org.zerock.ticketapiserver.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        log.info("--------------------");
        log.info(authentication);
        log.info("--------------------");

        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();

        Map< String , Object> claims = memberDTO.getClaims();

        String accessToken = JWTUtil.generateToken(claims,10);//지금 당장 사용
        String refreshToken = JWTUtil.generateToken(claims,60*24);//

        claims.put("accessToken",accessToken);
        claims.put("refreshToken",refreshToken);

        //json으로 변환
        Gson gson = new Gson();

        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");

        //출력
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();

    }
}
