package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotBlank(message = "email은 필수 항목 입니다")
    private String email;
    @NotBlank(message = "password는 필수 항목 입니다")
    private String password;
}
