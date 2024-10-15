package com.sparta.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {
    private Long userId;
    private String title;
    private String content;
}
