package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "workout_sessions")
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workoutTitle;
    private Integer duration;
    private Integer caloriesBurned;
    private String notes;
    private LocalDate completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public WorkoutSession() {
    }

    public WorkoutSession(String workoutTitle,
                          Integer duration,
                          Integer caloriesBurned,
                          String notes,
                          LocalDate completedAt,
                          User user) {
        this.workoutTitle = workoutTitle;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.notes = notes;
        this.completedAt = completedAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getWorkoutTitle() {
        return workoutTitle;
    }

    public void setWorkoutTitle(String workoutTitle) {
        this.workoutTitle = workoutTitle;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Integer caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDate completedAt) {
        this.completedAt = completedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
