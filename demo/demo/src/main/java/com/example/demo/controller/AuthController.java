package com.example.demo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.UserService;


@org.springframework.stereotype.Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response,
            Model model


    ){
        User user = userService.login(email, password);

        if (user == null) {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }

        Cookie cookie = new Cookie("userEmail", user.getEmail());
        cookie.setMaxAge(60 * 60);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "redirect:/profile";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(defaultValue = "false") boolean trainer,
            Model model
    ) {

        User user = new User(
                firstName,
                lastName,
                email,
                password,
                trainer
        );

        String result = userService.register(user);

        if (!result.equals("success")) {
            model.addAttribute("error", result);
            return "register";
        }

        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("userEmail", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "redirect:/login";
    }
}
