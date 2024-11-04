package com.sparta.schedule.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule.dto.LoginRequestDto;
import com.sparta.schedule.entity.UserRoleEnum;
import com.sparta.schedule.jwt.JwtUtil;
import com.sparta.schedule.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        // 아래 경로의 로그인 요청 처리하게 설정
        setFilterProcessesUrl("/api/user/login");
    }

    // 사용자 로그인 할 때 호출 되는 메서드, UsernamePasswordAuthenticationToken(이름,비번) 생성하여 인증 시도
    // 이름대신 이메일로 Spring Security 제공하는 기본 클래스라 변경하기 어려움. 안에 값만 바꿈
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    // 인증 성공
    // UserDetailsImpl에 getUsername()에서 이메일 받아 옴
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(email, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        log.info("인증 성공: 이메일 = {}, 역할 = {}, JWT 토큰 생성됨", email, role);
    }

    // 인증 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        int status = HttpServletResponse.SC_UNAUTHORIZED;
        String errorMessage = "이메일 또는 비밀번호를 잘못 입력하였습니다.";

        String jsonResponse = String.format("{\"status\": %d, \"errorMessage\": \"%s\"}", status, errorMessage);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush(); // 버퍼 비우기

    }
}
