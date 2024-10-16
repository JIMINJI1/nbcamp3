package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserRequestDto {
    @NotBlank(message = "패스워드는 필수 항목입니다.")
    private String password;
}
