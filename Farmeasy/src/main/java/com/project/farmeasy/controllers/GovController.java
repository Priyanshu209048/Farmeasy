package com.project.farmeasy.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gov")
@RequiredArgsConstructor
public class GovController {

    @GetMapping("/home")
    public String home(Model model) {
        return "gov/index";
    }

    @GetMapping("/analytics")
    public String analytics(Model model) {
        return "gov/analytics";
    }

    @GetMapping("/demo")
    public String demo(Model model) {
        return "gov/demo";
    }

    @GetMapping("/grievances")
    public String grievances(Model model) {
        return "gov/grievances";
    }

    @GetMapping("/overview")
    public String overview(Model model) {
        return "gov/overview";
    }

    @GetMapping("/policyNotification")
    public String policyNotification(Model model) {
        return "gov/policyNotification";
    }

    @GetMapping("/subsidisedLoans")
    public String subsidisedLoans(Model model) {
        return "gov/subsidisedLoans";
    }

}
