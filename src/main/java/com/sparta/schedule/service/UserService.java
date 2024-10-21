package com.sparta.schedule.service;

import com.sparta.schedule.dto.CreateUserRequestDto;
import com.sparta.schedule.dto.UpdateUserRequestDto;
import com.sparta.schedule.dto.UserResponseDto;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service

public class UserService {
    private final UserRepository userRepository;
    
    // 1. 유저 등록
    public UserResponseDto createUser(CreateUserRequestDto requestDto) {
        //dto -> entity
        User user = new User(
                requestDto.getUsername(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        //DB 저장
        User savedUser = userRepository.save(user);

        //entity -> responseDto
        UserResponseDto userResponseDto = new UserResponseDto(savedUser);

        return userResponseDto;
    }

    // 2. 유저 조회
    public UserResponseDto getUserById(Long userId) {
        // 주어진 ID로 유저 조회, 없으면 예외 발생
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        // entity -> responseDto
        return new UserResponseDto(
           user.getUserId(),
           user.getUsername(),
           user.getEmail(),
           user.getCreatedAt(),
           user.getUpdatedAt()
        );
    }


    // 3. 유저 수정
    @Transactional
    public UserResponseDto updateUser(Long userId, UpdateUserRequestDto requestDto) {
        // 주어진 ID로 유저 조회, 없으면 예외 발생
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

        // 유저의 비밀번호 수정
        user.updateUser(requestDto.getPassword());

        // 수정된 유저를 DB에 저장
        User updatedUser = userRepository.save(user);

        // 수정된 유저를 Response DTO로 변환하여 반환
        return new UserResponseDto(updatedUser);
    }


    // 4. 유저 삭제
    @Transactional
    public void deleteUser (Long userId){
        // 주어진 ID로 유저 조회, 없으면 예외 발생
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));

        // 유저 삭제
        userRepository.deleteById(userId);

    }

}

