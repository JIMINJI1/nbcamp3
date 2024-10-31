package com.sparta.schedule.config;

import com.sparta.schedule.filter.JwtAuthenticationFilter;
import com.sparta.schedule.filter.JwtAuthorizationFilter;
import com.sparta.schedule.jwt.JwtUtil;
import com.sparta.schedule.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    //    PasswordEncoder 빈 설정 (비밀번호)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager 빈 설정 (인증 처리)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //JwtAuthenticationFilter 빈 설정 (로그인 요청 시 사용자 정보 검증 jwt 생성)
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    //JwtAuthorizationFilter 빈 설정 (jwt 유효성 검증, 사용자 인증 상태 설정)
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    //SecurityFilterChain 빈 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/").permitAll() // 메인 페이지 요청 허가
                        .requestMatchers("/api/user/signup").permitAll() // '/api/user/signup'로 시작하는 요청 모두 접근 허가
                        .requestMatchers("/api/user/login").permitAll()
                        // 일정 수정,삭제 권한 -> 관리자만
                        .requestMatchers(HttpMethod.PUT, "/api/schedule/{scheduleId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/schedule/{scheduleId}").hasRole("ADMIN")
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        // 예외 처리 설정
        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증 예외 처리
                        .accessDeniedHandler(jwtAccessDeniedHandler) //인가 예외 처리
        );


        // 필터 관리
        // 필터 체인에서 필터 순서 지정
        // addFilterBefore(a,b) : a가 b보다 먼저 실행 되도록
        // 인증(Authentication) -> 인가(Authorization)
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
