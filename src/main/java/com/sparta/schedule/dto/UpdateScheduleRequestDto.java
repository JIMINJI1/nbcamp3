package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {
    @NotBlank(message="일정 제목은 필수 항목입니다.")
    private String title;
    @NotBlank(message="일정 내용은 필수 항목입니다.")
    private String content;
}
