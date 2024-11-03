package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    @NotBlank(message="일정 제목을 입력해 주세요")
    private String title;

    @NotBlank(message="일정 내용을 입력해 주세요")
    private String content;
}
