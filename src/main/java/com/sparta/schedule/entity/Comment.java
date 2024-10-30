package com.sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, updatable = false)
    private Long commentId;

    @Column(name = "comment", nullable = false)
    private String comment;

    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updatedAt;

    // 스케줄과 댓글 ( 하나의 스케줄 여러개 댓글)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    // 유저와 댓글 (한명의 유저의 여래개의 댓글)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(User user, String comment, Schedule schedule) {
        this.user = user;
        this.comment = comment;
        this.schedule = schedule;
    }

    // 댓글 수정
    public void updateComment(String comment) {
        this.comment = comment;
    }

}
