package com.sparta.schedule.repository;

import com.sparta.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
