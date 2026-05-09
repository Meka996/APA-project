package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "trainer_workouts")
public class TrainerWorkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer duration;
    private Integer caloriesTarget;
    private LocalDate assignedDate;
    private Integer actualDuration;
    private Integer actualCaloriesBurned;
    private LocalDate completedAt;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(columnDefinition = "TEXT")
    private String traineeNotes;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private User trainee;

    public TrainerWorkout() {
    }

    public TrainerWorkout(String title,
                          Integer duration,
                          Integer caloriesTarget,
                          LocalDate assignedDate,
                          String instructions,
                          User trainer,
                          User trainee) {
        this.title = title;
        this.duration = duration;
        this.caloriesTarget = caloriesTarget;
        this.assignedDate = assignedDate;
        this.instructions = instructions;
        this.trainer = trainer;
        this.trainee = trainee;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCaloriesTarget() {
        return caloriesTarget;
    }

    public void setCaloriesTarget(Integer caloriesTarget) {
        this.caloriesTarget = caloriesTarget;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Integer getActualDuration() {
        return actualDuration;
    }

    public void setActualDuration(Integer actualDuration) {
        this.actualDuration = actualDuration;
    }

    public Integer getActualCaloriesBurned() {
        return actualCaloriesBurned;
    }

    public void setActualCaloriesBurned(Integer actualCaloriesBurned) {
        this.actualCaloriesBurned = actualCaloriesBurned;
    }

    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDate completedAt) {
        this.completedAt = completedAt;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getTraineeNotes() {
        return traineeNotes;
    }

    public void setTraineeNotes(String traineeNotes) {
        this.traineeNotes = traineeNotes;
    }

    public boolean isCompleted() {
        return completedAt != null;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    public User getTrainee() {
        return trainee;
    }

    public void setTrainee(User trainee) {
        this.trainee = trainee;
    }
}
