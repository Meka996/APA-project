package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/trainee/dashboard")
    public String traineeDashboard(HttpServletRequest request) {
        User user = getLoggedInUser(request);

        if (user == null) {
            return "redirect:/login";
        }

        if (user.isTrainer()) {
            return "redirect:/trainer/dashboard";
        }

        return "trainee-dashboard";
    }

    @GetMapping("/trainer/dashboard")
    public String trainerDashboard(HttpServletRequest request) {
        User user = getLoggedInUser(request);

        if (user == null) {
            return "redirect:/login";
        }

        if (!user.isTrainer()) {
            return "redirect:/trainee/dashboard";
        }

        return "trainer-dashboard";
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
