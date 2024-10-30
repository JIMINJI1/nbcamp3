package com.sparta.schedule.contorller;


import com.sparta.schedule.dto.*;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")

public class UserController {
    private final UserService userService;

    // 1. 유저 등록 (회원가입)
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@Valid @RequestBody CreateUserRequestDto requestdto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(requestdto));
    }


    // 2. 유저 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto reqeustDto) {
        return ResponseEntity.ok(userService.login(reqeustDto));
    }


    /* 필수구현 */
    // 2. 유저 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.getUserById(userId, userDetails.getUser()));
    }

    // 3. 유저 수정
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto updatedUser = userService.updateUser(userId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(updatedUser);
    }

    // 4. 유저 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userId, userDetails.getUser());
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
