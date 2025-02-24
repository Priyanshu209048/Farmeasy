package com.project.farmeasy.controllers;

import com.project.farmeasy.dao.BankDao;
import com.project.farmeasy.dao.GrievencesDao;
import com.project.farmeasy.entities.Bank;
import com.project.farmeasy.entities.Grievences;
import com.project.farmeasy.services.GovService;
import com.project.farmeasy.services.impl.GovServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/gov")
@RequiredArgsConstructor
public class GovController {

    private final BankDao bankDao;
    private final GrievencesDao grievencesDao;
    private final GovService govService;

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
        List<Grievences> grievences = grievencesDao.findAll();
        model.addAttribute("grievences", grievences);
        return "gov/grievances";
    }

    @PostMapping("/grievences_process/{id}")
    public String grievances_process(@PathVariable("id") Integer id, @RequestParam("review") String review,
                                     @RequestParam("status") String status, Model model, Principal principal) {
        Grievences grievences = govService.getGrievences(id);
        model.addAttribute("grievences", grievences);
        return "redirect:gov/grievances";
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
