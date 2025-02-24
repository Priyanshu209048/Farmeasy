package com.project.farmeasy.services.impl;

import com.project.farmeasy.dao.GrievencesDao;
import com.project.farmeasy.dao.LoanFormDao;
import com.project.farmeasy.dao.FarmerDao;
import com.project.farmeasy.dao.UserDao;
import com.project.farmeasy.entities.Farmer;
import com.project.farmeasy.entities.Grievences;
import com.project.farmeasy.entities.LoanForm;
import com.project.farmeasy.entities.User;
import com.project.farmeasy.exceptions.ResourceNotFoundException;
import com.project.farmeasy.services.FarmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmerService {

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/documents";

    private final FarmerDao farmerDao;
    private final LoanFormDao loanFormDao;
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GrievencesDao grievencesDao;

    @Override
    public Farmer saveUser(Farmer farmer) {
        if (farmer.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        User user = new User();
        user.setEmail(farmer.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(farmer.getPassword()));
        user.setRole("ROLE_FARMER");
        userDao.save(user);
        return farmerDao.save(farmer);
    }

    @Override
    public Farmer updateUser(String email, Farmer farmer) {
        Farmer update = farmerDao.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Farmer", "email", email));
        update.setFirstName(farmer.getFirstName());
        update.setLastName(farmer.getLastName());
        update.setPassword(bCryptPasswordEncoder.encode(farmer.getPassword()));
        update.setContact(farmer.getContact());
        return farmerDao.save(update);
    }

    @Override
    public Farmer getUserByEmail(String email) {
        return farmerDao.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Farmer", "email", email));
    }

    @Override
    public List<Farmer> getAllUsers() {
        return farmerDao.findAll();
    }

    @Override
    public void deleteUser(String email) {
        Farmer farmer = farmerDao.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Farmer", "email", email));
        farmerDao.delete(farmer);
    }

    @Override
    public Boolean isUserExistByEmail(String email) {
        return farmerDao.existsByEmail(email);
    }

    @Override
    public Boolean isUserExistByUserid(String id) {
        return farmerDao.existsById(id);
    }

    @Override
    public void submitForm(LoanForm loanForm, MultipartFile file, String pdfName, int userId) throws IOException {
        System.out.println("Start submit form");
        String pdfUUID;
        if (!file.isEmpty()) {
            pdfUUID = userId+".pdf";
            Path fileNameAndPath = Paths.get(uploadDir, pdfUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            pdfUUID = pdfName;
        }

        loanForm.setPdfName(pdfUUID);
        System.out.println("End submit form");

        loanFormDao.save(loanForm);
    }

    @Override
    public Boolean isUserSubmittedForm(String email) {
        return loanFormDao.existsByEmail(email);
    }

    @Override
    public LoanForm getLoanFormByEmail(String email) {
        return loanFormDao.findByEmail(email);
    }

    @Override
    public LoanForm updateLoanForm(LoanForm loanForm) {
        loanFormDao.save(loanForm);
        return loanForm;
    }

    @Override
    public void addGrievence(Grievences grievence, Farmer farmer) {
        grievence.setFarmer(farmer);
        grievence.setGrievencesDate(LocalDate.now());
        grievence.setGrievencesReview("-");
        grievence.setGrievencesStatus("-");

        System.out.println("1");

        grievencesDao.save(grievence);
    }
}
