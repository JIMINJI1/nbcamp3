package com.sparta.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {
    private String username;
    private String title;
    private String content;
}
