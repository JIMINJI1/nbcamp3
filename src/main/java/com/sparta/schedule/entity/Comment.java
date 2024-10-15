package com.sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@Table(name="comment")
@NoArgsConstructor

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id", nullable=false, updatable=false)
    private Long commentId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "comment", nullable = false)
    private String comment;

    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "update_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(String username, String comment, Schedule schedule) {
        this.username = username;
        this.comment = comment;
        this.schedule = schedule;
    }

}
