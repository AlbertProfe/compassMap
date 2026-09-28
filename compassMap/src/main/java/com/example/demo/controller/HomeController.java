package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home1")
    public String home1(Model model) {
        model.addAttribute("title", "Compass Map");
        model.addAttribute("message", "Welcome to Compass Map!");
        return "home1";
    }

    @GetMapping("/home2")
    public String home2(Model model) {
        model.addAttribute("title", "Compass Map");
        model.addAttribute("message", "Welcome to Compass Map!");
        return "home2";
    }
}
