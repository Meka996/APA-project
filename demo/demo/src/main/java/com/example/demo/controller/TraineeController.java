package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.TrainerService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TraineeController {

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerService trainerService;

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

        return "trainee-trainers";
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
