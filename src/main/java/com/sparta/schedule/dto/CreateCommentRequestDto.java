package com.sparta.schedule.dto;

import lombok.Getter;

@Getter

public class CreateCommentRequestDto {
    private String username;
    private String comment;
    private Long scheduleId;
}
