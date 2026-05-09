package com.example.demo.service;

import com.example.demo.model.TrainerWorkout;
import com.example.demo.model.User;
import com.example.demo.repository.TrainerWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainerWorkoutService {

    @Autowired
    private TrainerWorkoutRepository trainerWorkoutRepository;

    public TrainerWorkout saveWorkout(TrainerWorkout trainerWorkout) {
        return trainerWorkoutRepository.save(trainerWorkout);
    }

    public List<TrainerWorkout> getWorkoutsForTraineeFromSelectedTrainer(User trainee) {
        if (trainee == null || trainee.getSelectedTrainer() == null) {
            return List.of();
        }

        return trainerWorkoutRepository.findByTrainerAndTraineeOrderByAssignedDateDesc(
                trainee.getSelectedTrainer(),
                trainee
        );
    }

    public List<TrainerWorkout> getWorkoutsForTrainerAndTrainee(User trainer, User trainee) {
        if (trainer == null || trainee == null) {
            return List.of();
        }

        return trainerWorkoutRepository.findByTrainerAndTraineeOrderByAssignedDateDesc(trainer, trainee);
    }

    public TrainerWorkout updateProgressForTrainee(Long workoutId,
                                                   User trainee,
                                                   Integer actualDuration,
                                                   Integer actualCaloriesBurned,
                                                   LocalDate completedAt,
                                                   String traineeNotes) {
        TrainerWorkout workout = trainerWorkoutRepository.findByIdAndTrainee(workoutId, trainee);

        if (workout == null) {
            return null;
        }

        User selectedTrainer = trainee.getSelectedTrainer();

        if (selectedTrainer == null || !workout.getTrainer().getId().equals(selectedTrainer.getId())) {
            return null;
        }

        workout.setActualDuration(actualDuration);
        workout.setActualCaloriesBurned(actualCaloriesBurned);
        workout.setCompletedAt(completedAt);
        workout.setTraineeNotes(traineeNotes);

        return trainerWorkoutRepository.save(workout);
    }
}
