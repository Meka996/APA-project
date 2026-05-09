package com.example.demo.controller;

import com.example.demo.model.TrainerWorkout;
import com.example.demo.model.User;
import com.example.demo.service.TrainerWorkoutService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class WorkoutController {

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerWorkoutService trainerWorkoutService;

    @GetMapping("/workout-track")
    public String workoutTrack(HttpServletRequest request, Model model) {
        String email = getUserEmail(request);

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        if (user == null) {
            return "redirect:/login";
        }

        if (user.isTrainer()) {
            return "redirect:/trainer/trainees";
        }

        List<TrainerWorkout> trainerWorkouts = trainerWorkoutService.getWorkoutsForTraineeFromSelectedTrainer(user);

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

        model.addAttribute("trainerWorkouts", trainerWorkouts);
        model.addAttribute("selectedTrainer", user.getSelectedTrainer());
        model.addAttribute("totalWorkouts", totalWorkouts);
        model.addAttribute("totalCalories", totalCalories);
        model.addAttribute("totalHours", totalMinutes / 60);
        model.addAttribute("readOnly", false);

        return "workout-track";
    }

    @PostMapping("/workout-track")
    public String saveWorkoutProgress(
            HttpServletRequest request,
            @RequestParam Long trainerWorkoutId,
            @RequestParam Integer duration,
            @RequestParam Integer caloriesBurned,
            @RequestParam(required = false) String notes,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate completedAt
    ) {
        String email = getUserEmail(request);

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        if (user == null) {
            return "redirect:/login";
        }

        if (user.isTrainer()) {
            return "redirect:/trainer/trainees";
        }

        TrainerWorkout updatedWorkout = trainerWorkoutService.updateProgressForTrainee(
                trainerWorkoutId,
                user,
                duration,
                caloriesBurned,
                completedAt,
                notes
        );

        if (updatedWorkout == null) {
            return "redirect:/workout-track?error=session";
        }

        return "redirect:/workout-track?success=session";
    }

    private String getUserEmail(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userEmail")) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
