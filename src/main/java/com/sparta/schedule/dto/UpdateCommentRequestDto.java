package com.sparta.schedule.dto;

import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {
    private String comment;
    private Long scheduleId;
}
