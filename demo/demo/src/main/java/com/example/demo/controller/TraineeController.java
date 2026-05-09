package com.example.demo.controller;

import com.example.demo.model.Trainer;
import com.example.demo.model.User;
import com.example.demo.service.TrainerWorkoutService;
import com.example.demo.service.TrainerService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TraineeController {

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private TrainerWorkoutService trainerWorkoutService;

    @GetMapping("/trainee/trainers")
    public String trainers(HttpServletRequest request, Model model) {
        User user = getLoggedInUser(request);

        if (user == null) {
            return "redirect:/login";
        }

        if (user.isTrainer()) {
            return "redirect:/trainer/dashboard";
        }

        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("selectedTrainer", user.getSelectedTrainer());

        return "trainee-trainers";
    }

    @PostMapping("/trainee/trainers/{trainerId}/choose")
    public String chooseTrainer(HttpServletRequest request, @PathVariable Long trainerId) {
        User user = getLoggedInUser(request);

        if (user == null) {
            return "redirect:/login";
        }

        if (user.isTrainer()) {
            return "redirect:/trainer/dashboard";
        }

        Trainer trainerProfile = trainerService.getTrainerById(trainerId);

        if (trainerProfile == null || trainerProfile.getUser() == null) {
            return "redirect:/trainee/trainers?error=trainer";
        }

        userService.chooseTrainer(user, trainerProfile.getUser());

        return "redirect:/trainee/trainers?success=trainer";
    }

    @GetMapping("/trainee/workouts")
    public String assignedWorkouts(HttpServletRequest request, Model model) {
        User user = getLoggedInUser(request);

        if (user == null) {
            return "redirect:/login";
        }

        if (user.isTrainer()) {
            return "redirect:/trainer/dashboard";
        }

        model.addAttribute("selectedTrainer", user.getSelectedTrainer());
        model.addAttribute("workouts", trainerWorkoutService.getWorkoutsForTraineeFromSelectedTrainer(user));

        return "trainee-workouts";
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
