package com.sparta.schedule.service;

import com.sparta.schedule.dto.*;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRoleEnum;
import com.sparta.schedule.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    // 1. 유저 등록
    public UserResponseDto createUser(CreateUserRequestDto requestDto) {
        //중복체크
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다");
        }

        // 비밀번호 해시 처리
        String hashPassword = hashPassword(requestDto.getPassword());

        // adminToken 체크
        if (requestDto.getAdminToken() != null && !requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
            throw new IllegalArgumentException("관리자 토큰이 일치하지 않습니다.");
        }
        // role 체크
        UserRoleEnum role = Optional.ofNullable(requestDto.getAdminToken())
                .filter(token -> token.equals(ADMIN_TOKEN))
                .map(token -> UserRoleEnum.ADMIN)
                .orElse(UserRoleEnum.USER);

        //dto -> entity
        User user = new User(
                requestDto.getUsername(),
                requestDto.getEmail(),
                hashPassword,
                role
        );

        //DB 저장
        User savedUser = userRepository.save(user);

        //entity -> responseDto
        UserResponseDto userResponseDto = new UserResponseDto(savedUser);

        return userResponseDto;
    }

    //2. 유저 로그인
    public LoginResponseDto login(LoginRequestDto requestDto) {
        // 유저 확인
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치 하지 않습니다.");
        }
        return new LoginResponseDto(user.getUsername());

    }

    // 3. 유저 조회
    public UserResponseDto getUserById(Long userId, User user) {
        // 주어진 ID로 유저 조회, 없으면 예외 발생
        User existingUser = validateUser(userId);

        // entity -> responseDto
        return new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


    // 4. 유저 수정
    @Transactional
    public UserResponseDto updateUser(Long userId, UpdateUserRequestDto requestDto, User user) {
        // 주어진 ID로 유저 조회, 없으면 예외 발생
        User existingUser = validateUser(userId);

        // 비밀번호 해시 처리
        String hashPassword = hashPassword(requestDto.getPassword());

        // 유저의 비밀번호 수정
        user.updateUser(hashPassword);

        // 수정된 유저를 DB에 저장
        User updatedUser = userRepository.save(user);

        // 수정된 유저를 Response DTO로 변환하여 반환
        return new UserResponseDto(updatedUser);
    }

    // 5. 유저 삭제
    @Transactional
    public void deleteUser(Long userId, User user) {
        // 주어진 ID로 유저 조회, 없으면 예외 발생
        User existingUser = validateUser(userId);

        // 유저 삭제
        userRepository.deleteById(userId);

    }

    // 유저 존재 확인 메소드
    public User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));
    }

    // 비밀번호 해시 처리 메소드
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
