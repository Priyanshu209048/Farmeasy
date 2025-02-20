package com.project.farmeasy.controllers;

import com.project.farmeasy.dao.LoanFormDao;
import com.project.farmeasy.entities.Farmer;
import com.project.farmeasy.entities.LoanForm;
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

@Controller
@RequestMapping("/farmer")
@RequiredArgsConstructor
public class UserController {

    private final FarmerServiceImpl farmerServiceImpl;
    private final LoanFormDao loanFormDao;

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
        System.out.println("Before");
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

}
