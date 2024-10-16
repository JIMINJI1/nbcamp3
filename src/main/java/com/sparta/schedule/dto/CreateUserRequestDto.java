package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter

public class CreateUserRequestDto {
    @NotBlank(message="이름은 필수 항목입니다.")
    private String username;

    @NotBlank(message="이메일은 필수 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
    message = "올바른 이메일 형식을 입력하세요.")
    private String email;

    @NotBlank(message=" 패스워드는 필수 항목입니다.")
    private String password;
}
