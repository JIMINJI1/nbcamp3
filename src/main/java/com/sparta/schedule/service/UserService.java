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
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        // entity -> responseDto
        return new UserResponseDto(
           user.getUserId(),
           user.getEmail(),
           user.getUsername(),
           user.getPassword(),
           user.getCreatedAt(),
           user.getUpdatedAt()

        );
    }


    // 3. 유저 수정
    @Transactional
    public UserResponseDto updateUser(Long userId, UpdateUserRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

        user.setPassword(requestDto.getPassword());

        User updatedUser = userRepository.save(user);

        return new UserResponseDto(updatedUser);
    }


    // 4. 유저 삭제
    @Transactional
    public void deleteUser (Long userId){
        User user = userRepository.findById(userId).orElse(null);

        if(user == null){
            throw new IllegalArgumentException("유저가 존재하지 않습니다");
        }
        userRepository.deleteById(userId);

    }

    
    
}

