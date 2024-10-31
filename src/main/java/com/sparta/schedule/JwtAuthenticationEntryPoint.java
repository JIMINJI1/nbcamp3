package com.sparta.schedule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String errorMessage = (String) request.getAttribute("exception");
        int status;

        if ("ExpiredJwtException".equals(errorMessage)) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "토큰이 만료되었습니다."; // 401
        } else if ("TokenMissing".equals(errorMessage)) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            errorMessage = "토큰이 없습니다."; // 400
        } else {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "인증 실패"; // 401
        }

        // JSON 형태로 응답 생성
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // JSON 응답 생성
        String jsonResponse = String.format("{\"status\": %d, \"errorMessage\": \"%s\"}", status, errorMessage);
        response.getWriter().write(jsonResponse);

    }
}
