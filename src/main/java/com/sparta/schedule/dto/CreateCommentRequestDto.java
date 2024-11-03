package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter

public class CreateCommentRequestDto {
    @NotBlank(message = "댓글 내용을 입력해 주세요")
    private String comment;
}
