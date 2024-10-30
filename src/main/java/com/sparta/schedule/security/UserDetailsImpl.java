package com.sparta.schedule.security;

import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    // User는 조회한 사용자 정보 가지고 있음
    private final User user;

    // User 객체 받아서 UserDetailsImpls 생성 => UserDetails객체에 사용자 정보 담을 수 있음
    public UserDetailsImpl(User user) {
        this.user = user;
    }

    // User 객체 자체를 반환, 사용자 추가정보 필요할 때 사용
    public User getUser() {
        return user;
    }

    // 사용자 암호 반환 => 인증 처리 시 비밀번호 확인때 사용
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 이름(ID)반환 => 인증 과정에서 식별자로 사용 => 이름에서 이메일로 변경
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // 권한 반환 => 인가 처리에 사용
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = user.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    // 계정 관련 메소드  - 계정 만료 체크
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠겨있는지 체크
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호(자격증명) 만료 체크
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 체크
    @Override
    public boolean isEnabled() {
        return true;
    }
}
