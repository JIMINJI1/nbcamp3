package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    @NotBlank(message="일정 제목은 필수 항목입니다.")
    private String title;

    @NotBlank(message="일정 내용은 필수 항목입니다.")
    private String content;
}
