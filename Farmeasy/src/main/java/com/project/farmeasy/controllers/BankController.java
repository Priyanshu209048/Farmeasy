package com.project.farmeasy.controllers;

import com.project.farmeasy.dao.BankDao;
import com.project.farmeasy.dao.GrievencesDao;
import com.project.farmeasy.entities.*;
import com.project.farmeasy.services.BankService;
import com.project.farmeasy.services.FarmerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;
    private final BankDao bankDao;
    private final GrievencesDao grievencesDao;

    @GetMapping("/home")
    public String home(Model model) {
        return "bank/index";
    }

    @GetMapping("/addScheme")
    public String addScheme(Model model) {
        model.addAttribute("scheme", new Scheme());
        return "bank/addScheme";
    }

    @GetMapping("/schemes")
    public String schemes(Model model, Principal principal) {
        String username = principal.getName();
        List<Scheme> scheme = bankService.getSchemes(username);
        model.addAttribute("scheme", scheme);
        return "bank/schemes";
    }

    @GetMapping("/ldstracker")
    public String ldstracker(Model model) {
        return "bank/ldstracker";
    }

    @GetMapping("/loanReports")
    public String loanReports(Model model) {
        return "bank/loanReports";
    }

    @PostMapping("/do_scheme")
    public String schemeProcess(@Valid @ModelAttribute("scheme") Scheme sc, Model model, Principal principal) {
        try {
            System.out.println("Begin");
            String username = principal.getName();
            Bank bank = bankDao.findByEmail(username);
            Scheme scheme = bankService.addScheme(sc, bank);
            model.addAttribute("scheme", scheme);
            System.out.println("End");
            return "bank/index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("scheme", new Scheme());
            return "bank/addScheme";
        }
    }

    @GetMapping("/loanApplications")
    public String loanApplication(Model model, Principal principal) {
        String username = principal.getName();
        List<Apply> applies = bankService.getApplyByBank(username);
        model.addAttribute("applies", applies);
        return "bank/loanApplications";
    }

    @PostMapping("/do_loanApplications/{id}")
    public String loanApplicationProcess(@PathVariable("id") Integer id, @RequestParam("review") String review,
                                         @RequestParam("status") String status, Model model, Principal principal) {
        Apply apply = bankService.getApply(id);
        System.out.println(review);

        bankService.updateApply(apply, status, review);

        String username = principal.getName();
        List<Apply> applies = bankService.getApplyByBank(username);
        model.addAttribute("applies", applies);

        return "redirect:/bank/loanApplications";
    }

    @GetMapping("/grievances")
    public String grievance(Model model, Principal principal) {
        String username = principal.getName();
        Bank bank = bankDao.findByEmail(username);
        List<Grievences> grievences = grievencesDao.findAllByBank(bank);
        model.addAttribute("grievences", grievences);
        return "bank/grievances";
    }

}
