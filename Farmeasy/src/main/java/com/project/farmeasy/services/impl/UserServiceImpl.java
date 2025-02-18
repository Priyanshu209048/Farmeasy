package com.project.farmeasy.services.impl;

import com.project.farmeasy.dao.LoanFormDao;
import com.project.farmeasy.dao.UserDao;
import com.project.farmeasy.entities.LoanForm;
import com.project.farmeasy.entities.User;
import com.project.farmeasy.exceptions.ResourceNotFoundException;
import com.project.farmeasy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/documents";

    private final UserDao userDao;
    private final LoanFormDao loanFormDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) {
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        user.setId(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_FARMER");
        return userDao.save(user);
    }

    @Override
    public User updateUser(String email, User user) {
        User update = userDao.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User", "email", email));
        update.setFirstName(user.getFirstName());
        update.setLastName(user.getLastName());
        update.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        update.setContact(user.getContact());
        return userDao.save(update);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void deleteUser(String email) {
        User user = userDao.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User", "email", email));
        userDao.delete(user);
    }

    @Override
    public Boolean isUserExistByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public Boolean isUserExistByUserid(String id) {
        return userDao.existsById(id);
    }

    @Override
    public void submitForm(LoanForm loanForm, MultipartFile file, String pdfName) throws IOException {
        System.out.println("Start submit form");
        String pdfUUID;
        if (!file.isEmpty()) {
            pdfUUID = file.getOriginalFilename();
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
}
