package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long scheduleId;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<CommentResponseDto> comment;



    public ScheduleResponseDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.username = schedule.getUser() != null ? schedule.getUser().getUsername() : null;
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();

        this.comment = schedule.getComments().stream()
                .map(CommentResponseDto::new).toList();
    }

}
