package com.sparta.schedule.contorller;


import com.sparta.schedule.dto.CreateUserRequestDto;
import com.sparta.schedule.dto.UpdateUserRequestDto;
import com.sparta.schedule.dto.UserResponseDto;
import com.sparta.schedule.service.ScheduleService;
import com.sparta.schedule.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")

public class UserController {
    private final UserService userService;
    private final ScheduleService scheduleService;

    // 1. 유저 등록
    @PostMapping("")
    public UserResponseDto createUser(@Valid @RequestBody CreateUserRequestDto requestdto) {
        return userService.createUser(requestdto);
    }

    // 2. 유저 조회
    @GetMapping("/{userId}")
    public UserResponseDto getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    // 3. 유저 수정
    @PutMapping("/{userId}")
    public UserResponseDto updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserRequestDto requestdto) {
        return userService.updateUser(userId,requestdto);
    }

    // 4. 유저 삭제
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

    }
}
