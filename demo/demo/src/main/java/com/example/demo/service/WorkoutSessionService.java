package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.WorkoutSession;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkoutSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkoutSessionService {

    @Autowired
    private WorkoutSessionRepository workoutSessionRepository;

    @Autowired
    private UserRepository userRepository;

    public WorkoutSession saveWorkoutSession(WorkoutSession workoutSession) {
        return workoutSessionRepository.save(workoutSession);
    }

    public WorkoutSession saveWorkoutSessionForUser(String userEmail, WorkoutSession workoutSession) {
        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            return null;
        }

        workoutSession.setUser(user);

        return workoutSessionRepository.save(workoutSession);
    }

    public List<WorkoutSession> getWorkoutHistory() {
        return workoutSessionRepository.findAll();
    }

    public List<WorkoutSession> getWorkoutHistoryByUser(User user) {
        return workoutSessionRepository.findByUserOrderByCompletedAtDesc(user);
    }

    public List<WorkoutSession> getWorkoutHistoryByUserEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            return List.of();
        }

        return workoutSessionRepository.findByUserOrderByCompletedAtDesc(user);
    }

    public List<WorkoutSession> getWorkoutHistoryByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return List.of();
        }

        return workoutSessionRepository.findByUserOrderByCompletedAtDesc(user);
    }

    public void deleteWorkoutSession(Long sessionId) {
        workoutSessionRepository.deleteById(sessionId);
    }

    @Transactional
    public boolean deleteWorkoutSessionForUser(Long sessionId, String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        if (user == null || !workoutSessionRepository.existsByIdAndUser(sessionId, user)) {
            return false;
        }

        workoutSessionRepository.deleteByIdAndUser(sessionId, user);

        return true;
    }
}
