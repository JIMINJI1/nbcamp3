package com.sparta.schedule.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String username;

    public LoginResponseDto(String username) {
        this.username = username;
    }
}
