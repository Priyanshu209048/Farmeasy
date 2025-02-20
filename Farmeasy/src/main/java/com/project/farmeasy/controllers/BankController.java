package com.project.farmeasy.controllers;

import com.project.farmeasy.dao.BankDao;
import com.project.farmeasy.entities.Bank;
import com.project.farmeasy.entities.Farmer;
import com.project.farmeasy.entities.Scheme;
import com.project.farmeasy.services.BankService;
import com.project.farmeasy.services.FarmerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;
    private final BankDao bankDao;

    @GetMapping("/home")
    public String home(Model model) {
        return "bank/index";
    }

    @GetMapping("/grievance")
    public String grievance(Model model) {
        return "bank/grievance";
    }

    @GetMapping("/addScheme")
    public String scheme(Model model) {
        model.addAttribute("scheme", new Scheme());
        return "bank/addScheme";
    }

    @PostMapping("/do_scheme")
    public String schemeProcess(@Valid @ModelAttribute("scheme") Scheme sc, Model model, Principal principal) {
        try {
            String username = principal.getName();
            Bank bank = bankDao.findByEmail(username);
            Scheme scheme = bankService.addScheme(sc, bank);
            model.addAttribute("scheme", scheme);
            return "bank/index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("scheme", new Scheme());
            return "bank/addScheme";
        }
    }

}
