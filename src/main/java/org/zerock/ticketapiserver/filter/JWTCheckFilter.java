package org.zerock.ticketapiserver.filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.ticketapiserver.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("---------------------------");

        log.info("---------------------------");

        log.info("---------------------------");

        String authHeaderStr = request.getHeader("Authorization");

        try{
            //Bearer //7개 JWT문자열
            //앞의 7개는 잘라낸다
            String accessToken = authHeaderStr.substring(7);
            //검사 후 결과 -> 예외 발생
            Map<String,Object> claims = JWTUtil.validateToken(accessToken);

            log.info(claims);

            //dest 다음목적지
            filterChain.doFilter(request,response);

        }catch (Exception e){

            log.error("JWT Check Error........");
            log.error(e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //체크를 하면 안되는 항목
        //true == not checking
        String path = request.getRequestURI();

        log.info("check uri==---" + path);

        if(path.startsWith("/api/member")){
            return true; //체크 안함
        }

        //false == check
        return false;
    }
}
