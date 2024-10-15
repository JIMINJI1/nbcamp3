package com.sparta.schedule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter

public class CreateUserRequestDto {
    private String username;
    private String email;
    private String password;
}
