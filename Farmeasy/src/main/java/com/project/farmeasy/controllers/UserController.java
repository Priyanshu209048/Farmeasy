package com.project.farmeasy.controllers;

import com.project.farmeasy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/home")
    public String home(Model model) {
        return "farmer/dashboard";
    }

    @GetMapping("/schemes")
    public String schemes(Model model) {
        return "farmer/schemes";
    }

    @GetMapping("/soilQuality")
    public String soilQuality(Model model) {
        return "farmer/soilQuality";
    }

    @GetMapping("/calender")
    public String calender(Model model) {
        return "farmer/calender";
    }

    @GetMapping("/loan")
    public String loan(Model model) {
        return "farmer/loan";
    }

}
