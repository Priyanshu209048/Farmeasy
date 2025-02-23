package com.project.farmeasy.controllers;

import com.project.farmeasy.dao.ApplyDao;
import com.project.farmeasy.dao.LoanFormDao;
import com.project.farmeasy.dao.SchemeDao;
import com.project.farmeasy.entities.*;
import com.project.farmeasy.services.BankService;
import com.project.farmeasy.services.impl.FarmerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/farmer")
@RequiredArgsConstructor
public class UserController {

    private final FarmerServiceImpl farmerServiceImpl;
    private final LoanFormDao loanFormDao;
    private final BankService bankService;
    private final ApplyDao applyDao;
    private final SchemeDao schemeDao;

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

    @GetMapping("/form")
    public String form(Model model, Principal principal) {
        if (!farmerServiceImpl.isUserSubmittedForm(principal.getName())) {
            model.addAttribute("loanForm", new LoanForm());
            return "farmer/form";
        } else {
            LoanForm loanForm = farmerServiceImpl.getLoanFormByEmail(principal.getName());
            model.addAttribute("loanForm", loanForm);
            return "farmer/updateForm";
        }
    }

    @PostMapping("/form")
    public String loanFormProcess(@Valid @ModelAttribute("loanForm") LoanForm loanForm, Principal principal,
                                  @RequestParam("documents") MultipartFile file, @RequestParam("pdfName") String pdfName, Model model) throws IOException {
        if (!loanForm.getEmail().equals(principal.getName())) {
            return "farmer/form";
        }
        Farmer farmer = farmerServiceImpl.getUserByEmail(principal.getName());
        farmerServiceImpl.submitForm(loanForm, file, pdfName, farmer.getId());
        model.addAttribute("loanForm", loanForm);
        return "farmer/dashboard";
    }

    @PostMapping("/updateForm")
    public String UpdateLoanFormProcess(@Valid @ModelAttribute("loanForm") LoanForm loanForm, Principal principal,
                                  @RequestParam("documents") MultipartFile file, @RequestParam("pdfName") String pdfName, Model model) throws IOException {
        System.out.println("Before");
        if (!loanForm.getEmail().equals(principal.getName())) {
            return "farmer/updateForm";
        }
        Farmer farmer = farmerServiceImpl.getUserByEmail(principal.getName());
        farmerServiceImpl.submitForm(loanForm, file, pdfName, farmer.getId());
        System.out.println("After");
        model.addAttribute("loanForm", loanForm);
        return "farmer/dashboard";
    }

    @GetMapping("/download")
    @ResponseBody
    public FileSystemResource download(@Param(value = "id") Integer id) {
        LoanForm loanForm = loanFormDao.findById(id).orElse(null);
        assert loanForm != null;
        //return new FileSystemResource(new File("D:/SpringBootProject/Farmeasy/src/main/resources/static/documents/" + loanForm.getPdfName()));
        return new FileSystemResource(new File(System.getProperty("user.dir") + "/src/main/resources/static/documents" + File.separator + loanForm.getPdfName()));
    }

    @GetMapping("/schemeApply")
    public String schemeApply(Model model, Principal principal) {
        String username = principal.getName();
        Farmer farmer = farmerServiceImpl.getUserByEmail(username);
        List<Scheme> scheme = bankService.getSchemes();

        model.addAttribute("farmer", farmer);
        model.addAttribute("scheme", scheme);
        model.addAttribute("apply", new Apply());

        return "farmer/schemeApply";
    }

    @PostMapping("/apply/{id}")
    public String applyProcess(@PathVariable("id") Integer id, @RequestParam("amount") String amount, Model model, Principal principal) {
        String username = principal.getName();
        Farmer farmer = farmerServiceImpl.getUserByEmail(username);
        Scheme scheme = schemeDao.findById(id).orElse(null);

        System.out.println(amount);
        Apply apply = new Apply();
        apply.setFarmer(farmer);
        apply.setScheme(scheme);
        apply.setDate(LocalDate.now());
        apply.setAmount(amount);
        apply.setStatusDate("-");
        apply.setStatus("Pending");
        applyDao.save(apply);
        return "redirect:/farmer/home";
    }

    @GetMapping("/grievences")
    public String grievences(Model model) {
        model.addAttribute("grievences", new Grievences());
        model.addAttribute("farmer", new Farmer());
        model.addAttribute("banks", bankService.getBanks());
        return "farmer/grievences";
    }

    @PostMapping("/processGrievences")
    public String processGrievences(@Valid @ModelAttribute("grievences") Grievences grievences, Model model, Principal principal) {
        String username = principal.getName();
        Farmer farmer = farmerServiceImpl.getUserByEmail(username);

        System.out.println(grievences.getGrievencesReview());
        System.out.println(grievences.getGrievencesType());
        System.out.println(grievences.getBank().getBankName());
        return "redirect:/farmer/home";
    }

    @GetMapping("/status")
    public String status(Model model) {
        return "farmer/status";
    }

}
