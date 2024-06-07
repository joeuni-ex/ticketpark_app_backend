package org.zerock.ticketapiserver.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        log.info("Login fail...."+ exception);

        Gson gson = new Gson();
        //에러 로그인 메시지 출력
        String jsonStr = gson.toJson(Map.of("error","ERROR_LOGIN"));
        //json 문자열로 만들어줌
        //상태 코드는 200 -> 정상적으로 응답은 해주되, 로그인 실패했다는 메시지로만 전송
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}
