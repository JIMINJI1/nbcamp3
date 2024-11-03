package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter

public class CreateUserRequestDto {
    @NotBlank(message="이름을 입력해 주세요")
    private String username;

    @NotBlank(message="이메일을 입력해 주세요")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
    message = "올바른 이메일 형식을 입력하세요.")
    private String email;

    @NotBlank(message=" 비밀번호를 입력해 주세요")
    private String password;

    
    //관리자 가입용 adminToken
    private String adminToken;

}
