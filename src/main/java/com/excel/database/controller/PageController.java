package com.excel.database.controller;

import com.excel.database.jwt.JwtResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/redirect-after-login")
    public String roles(HttpSession session, Model model) {

        String hello = "This is Hello";
        model.addAttribute("hello", hello);
        // Retrieve JwtResponse from session
        JwtResponse response = (JwtResponse) session.getAttribute("jwtResponse");

        if (response != null) {
            // Add JwtResponse object to the model
            model.addAttribute("roles", response.getRoles());

            // Return the roles page
            return "roles";
        } else {
            // Redirect to login page if JwtResponse is not found in session
            return "redirect:/login";
        }
    }
}
