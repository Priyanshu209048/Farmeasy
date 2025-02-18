package com.project.farmeasy.services;

import com.project.farmeasy.entities.LoanForm;
import com.project.farmeasy.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User updateUser(String email, User user);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(String email);
    Boolean isUserExistByEmail(String email);
    Boolean isUserExistByUserid(String id);

    void submitForm(LoanForm loanForm, MultipartFile file, String fileName) throws IOException;
    Boolean isUserSubmittedForm(String email);
    LoanForm getLoanFormByEmail(String email);
    LoanForm updateLoanForm(LoanForm loanForm);
}
