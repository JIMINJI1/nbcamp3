package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {
    @NotBlank(message="comment은 필수 항목입니다.")
    private String comment;
}
