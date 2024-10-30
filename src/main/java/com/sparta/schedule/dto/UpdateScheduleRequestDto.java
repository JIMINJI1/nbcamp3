package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {
    @NotBlank(message = "title은 필수 항목입니다.")
    private String title;
    @NotBlank(message = "content은 필수 항목입니다.")
    private String content;
}
