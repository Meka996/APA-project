package com.example.demo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.UserService;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(HttpServletRequest request, Model model) {

        Cookie[] cookies = request.getCookies();

        String email = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userEmail")) {
                    email = cookie.getValue();
                }
            }
        }

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            HttpServletRequest request,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false) Double height,
            @RequestParam(required = false) String fitnessGoal
    ) {

        Cookie[] cookies = request.getCookies();

        String email = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userEmail")) {
                    email = cookie.getValue();
                }
            }
        }

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(email);

        if (user == null) {
            return "redirect:/login";
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setWeight(weight);
        user.setHeight(height);
        user.setFitnessGoal(fitnessGoal);

        userService.updateUser(user);

        return "redirect:/profile";
    }
}
