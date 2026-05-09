package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.TrainerWorkout;
import com.example.demo.model.WorkoutSession;
import com.example.demo.service.TrainerWorkoutService;
import com.example.demo.service.UserService;
import com.example.demo.service.WorkoutSessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TrainerController {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutSessionService workoutSessionService;

    @Autowired
    private TrainerWorkoutService trainerWorkoutService;

    @GetMapping("/trainer/trainees")
    public String trainees(
            HttpServletRequest request,
            @RequestParam(defaultValue = "users") String mode,
            Model model
    ) {
        User trainer = getLoggedInUser(request);

        if (trainer == null) {
            return "redirect:/login";
        }

        if (!trainer.isTrainer()) {
            return "redirect:/trainee/dashboard";
        }

        String pageMode = normalizeMode(mode);

        model.addAttribute("trainees", userService.getTraineesForTrainer(trainer));
        model.addAttribute("mode", pageMode);
        model.addAttribute("pageTitle", getPageTitle(pageMode));
        model.addAttribute("pageSubtitle", getPageSubtitle(pageMode));

        return "trainer-trainees";
    }

    @GetMapping("/trainer/trainees/{traineeId}/workouts")
    public String traineeWorkoutHistory(
            HttpServletRequest request,
            @PathVariable Long traineeId,
            Model model
    ) {
        User trainer = getLoggedInUser(request);

        if (trainer == null) {
            return "redirect:/login";
        }

        if (!trainer.isTrainer()) {
            return "redirect:/trainee/dashboard";
        }

        User trainee = userService.getUserById(traineeId);

        if (trainee == null || trainee.isTrainer()) {
            return "redirect:/trainer/trainees";
        }

        if (!userService.isTraineeAssignedToTrainer(trainee, trainer)) {
            return "redirect:/trainer/trainees";
        }

        List<TrainerWorkout> trainerWorkouts = trainerWorkoutService.getWorkoutsForTrainerAndTrainee(trainer, trainee);

        int totalWorkouts = (int) trainerWorkouts.stream()
                .filter(TrainerWorkout::isCompleted)
                .count();
        int totalCalories = trainerWorkouts.stream()
                .map(TrainerWorkout::getActualCaloriesBurned)
                .filter(calories -> calories != null)
                .mapToInt(Integer::intValue)
                .sum();
        int totalMinutes = trainerWorkouts.stream()
                .map(TrainerWorkout::getActualDuration)
                .filter(duration -> duration != null)
                .mapToInt(Integer::intValue)
                .sum();

        model.addAttribute("viewingTrainee", trainee);
        model.addAttribute("readOnly", true);
        model.addAttribute("trainerWorkouts", trainerWorkouts);
        model.addAttribute("selectedTrainer", trainer);
        model.addAttribute("totalWorkouts", totalWorkouts);
        model.addAttribute("totalCalories", totalCalories);
        model.addAttribute("totalHours", totalMinutes / 60);

        return "workout-track";
    }

    @GetMapping("/trainer/trainees/{traineeId}/workouts/create")
    public String createWorkoutForm(
            HttpServletRequest request,
            @PathVariable Long traineeId,
            Model model
    ) {
        User trainer = getLoggedInUser(request);

        if (trainer == null) {
            return "redirect:/login";
        }

        if (!trainer.isTrainer()) {
            return "redirect:/trainee/dashboard";
        }

        User trainee = userService.getUserById(traineeId);

        if (!userService.isTraineeAssignedToTrainer(trainee, trainer)) {
            return "redirect:/trainer/trainees?mode=create";
        }

        model.addAttribute("trainee", trainee);

        return "trainer-create-workout";
    }

    @PostMapping("/trainer/trainees/{traineeId}/workouts/create")
    public String createWorkout(
            HttpServletRequest request,
            @PathVariable Long traineeId,
            @RequestParam String title,
            @RequestParam Integer duration,
            @RequestParam(required = false) Integer caloriesTarget,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate assignedDate,
            @RequestParam(required = false) String instructions
    ) {
        User trainer = getLoggedInUser(request);

        if (trainer == null) {
            return "redirect:/login";
        }

        if (!trainer.isTrainer()) {
            return "redirect:/trainee/dashboard";
        }

        User trainee = userService.getUserById(traineeId);

        if (!userService.isTraineeAssignedToTrainer(trainee, trainer)) {
            return "redirect:/trainer/trainees?mode=create";
        }

        TrainerWorkout trainerWorkout = new TrainerWorkout(
                title,
                duration,
                caloriesTarget,
                assignedDate,
                instructions,
                trainer,
                trainee
        );

        trainerWorkoutService.saveWorkout(trainerWorkout);

        return "redirect:/trainer/trainees?mode=create&success=workout";
    }

    private User getLoggedInUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userEmail")) {
                return userService.getUserByEmail(cookie.getValue());
            }
        }

        return null;
    }

    private String normalizeMode(String mode) {
        if ("create".equals(mode) || "history".equals(mode)) {
            return mode;
        }

        return "users";
    }

    private String getPageTitle(String mode) {
        if ("create".equals(mode)) {
            return "Create Workout";
        }

        if ("history".equals(mode)) {
            return "Workout History";
        }

        return "Assigned Trainees";
    }

    private String getPageSubtitle(String mode) {
        if ("create".equals(mode)) {
            return "Select one of your trainees to create a workout plan.";
        }

        if ("history".equals(mode)) {
            return "Select one of your trainees to review completed workouts.";
        }

        return "View trainees who selected you as their trainer.";
    }
}
