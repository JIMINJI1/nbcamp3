package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class CommentResponseDto {
    private Long commentId;
    private String username;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ScheduleResponseDto schedule;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.schedule = new ScheduleResponseDto(comment.getSchedule());
    }


}
