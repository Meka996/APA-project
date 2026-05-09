package com.example.demo.repository;

import com.example.demo.model.TrainerWorkout;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerWorkoutRepository extends JpaRepository<TrainerWorkout, Long> {

    List<TrainerWorkout> findByTrainerAndTraineeOrderByAssignedDateDesc(User trainer, User trainee);

    TrainerWorkout findByIdAndTrainee(Long id, User trainee);
}
