package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter

public class CreateCommentRequestDto {
    @NotBlank(message="이름은 필수 항목입니다.")
    private String username;
    @NotBlank(message="댓글은 필수 항목입니다.")
    private String comment;
    @NotNull
    private Long scheduleId;
}
