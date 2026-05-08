package com.example.demo.repository;

import com.example.demo.model.WorkoutSession;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutSessionRepository
        extends JpaRepository<WorkoutSession, Long> {

    List<WorkoutSession> findByUser(User user);

    List<WorkoutSession> findByUserOrderByCompletedAtDesc(User user);

    boolean existsByIdAndUser(Long id, User user);

    void deleteByIdAndUser(Long id, User user);
}
