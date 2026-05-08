package com.example.demo.controller;

import com.example.demo.model.WorkoutSession;
import com.example.demo.model.User;
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
public class WorkoutController {

    @Autowired
    private WorkoutSessionService workoutSessionService;

    @Autowired
    private UserService userService;

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

        List<WorkoutSession> sessions = workoutSessionService.getWorkoutHistoryByUserEmail(email);

        int totalWorkouts = sessions.size();
        int totalCalories = sessions.stream()
                .map(WorkoutSession::getCaloriesBurned)
                .filter(calories -> calories != null)
                .mapToInt(Integer::intValue)
                .sum();
        int totalMinutes = sessions.stream()
                .map(WorkoutSession::getDuration)
                .filter(duration -> duration != null)
                .mapToInt(Integer::intValue)
                .sum();

        model.addAttribute("sessions", sessions);
        model.addAttribute("totalWorkouts", totalWorkouts);
        model.addAttribute("totalCalories", totalCalories);
        model.addAttribute("totalHours", totalMinutes / 60);

        return "workout-track";
    }

    @PostMapping("/workout-track")
    public String saveWorkoutSession(
            HttpServletRequest request,
            @RequestParam String workoutTitle,
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

        WorkoutSession workoutSession = new WorkoutSession(
                workoutTitle,
                duration,
                caloriesBurned,
                notes,
                completedAt,
                null
        );

        workoutSessionService.saveWorkoutSessionForUser(email, workoutSession);

        return "redirect:/workout-track";
    }

    @PostMapping("/workout-track/{sessionId}/delete")
    public String deleteWorkoutSession(
            HttpServletRequest request,
            @PathVariable Long sessionId
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

        workoutSessionService.deleteWorkoutSessionForUser(sessionId, email);

        return "redirect:/workout-track";
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
