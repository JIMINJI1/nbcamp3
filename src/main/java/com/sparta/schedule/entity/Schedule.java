package com.sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Table(name="schedule")
@NoArgsConstructor

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_id", nullable=false, updatable=false)
    private Long scheduleId;

    // @Column(name="username", nullable=false)
    //private String username;
    // =>  username 대신 user_id로

    @Column(name="title", nullable=false)
    private String title;

    @Column(name="content", nullable=false)
    private String content;

    @CreationTimestamp
    @Column(name="create_date", nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="update_date", nullable=false)
    private LocalDateTime updatedAt;

    // 스케줄과 댓글 (하나의 스케줄 여러개 댓글)
    @OneToMany(mappedBy ="schedule", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 스케줄과 유저 (한 유저의 여러개 스케줄)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public Schedule(User user,  String title,  String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public Schedule(Long scheduleId, User user, String title,  String content,  LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
        this.scheduleId = scheduleId;
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
