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
@Table(name ="user")
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", nullable=false, updatable=false)
    private Long userId;

    @Column(name="username", nullable=false)
    private String username;

    @Column(name="email", nullable=false)
    private String email;

    @Column(name="password",nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name="create_date", nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="update_date", nullable=false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();


    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void updateUser(String password){
        this.password = password;
    }
}
