package com.sparta.schedule.filter;

import com.sparta.schedule.jwt.JwtUtil;
import com.sparta.schedule.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더에서 jwt 추출
        String tokenValue = jwtUtil.getJwtFromHeader(req);

        // 토큰이 없거나 비어있는 경우 처리
        if (!StringUtils.hasText(tokenValue)) {
            log.error("토큰이 없습니다."); // 로그 추가
            req.setAttribute("exception", "TokenMissing"); // 예외 처리 속성 설정
            filterChain.doFilter(req, res); // 다음 필터로 진행
            return; // 필터 체인 종료
        }

        // 토큰이 있는 경우 검증
        if (!jwtUtil.validateToken(tokenValue)) {
            log.error("Token Error");
            req.setAttribute("exception", "ExpiredJwtException"); // 예외 처리 속성 설정
            filterChain.doFilter(req, res); // 다음 필터로 진행
            return; // 필터 체인 종료
        }

        // 유효한 토큰의 경우 사용자 정보를 추출하여 인증 설정
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        try {
            setAuthentication(info.getSubject());
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }

        // 다음 필터로 진행
        filterChain.doFilter(req, res);

    }


    // 인증 처리
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email); //이메일로 사용자 로드
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
