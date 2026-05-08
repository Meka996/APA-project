package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.WorkoutSession;
import com.example.demo.service.UserService;
import com.example.demo.service.WorkoutSessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TrainerController {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutSessionService workoutSessionService;

    @GetMapping("/trainer/trainees")
    public String trainees(HttpServletRequest request, Model model) {
        User trainer = getLoggedInUser(request);

        if (trainer == null) {
            return "redirect:/login";
        }

        if (!trainer.isTrainer()) {
            return "redirect:/trainee/dashboard";
        }

        model.addAttribute("trainees", userService.getTrainees());

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

        List<WorkoutSession> sessions = workoutSessionService.getWorkoutHistoryByUserId(traineeId);

        model.addAttribute("trainee", trainee);
        model.addAttribute("sessions", sessions);

        return "trainer-trainee-workouts";
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
}
