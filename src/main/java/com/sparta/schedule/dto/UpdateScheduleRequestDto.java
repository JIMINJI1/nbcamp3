package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {
    private String title;
    private String content;
}
