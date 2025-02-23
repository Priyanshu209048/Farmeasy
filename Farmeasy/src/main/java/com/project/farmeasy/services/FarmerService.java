package com.project.farmeasy.services;

import com.project.farmeasy.entities.LoanForm;
import com.project.farmeasy.entities.Farmer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FarmerService {
    Farmer saveUser(Farmer farmer);
    Farmer updateUser(String email, Farmer farmer);
    Farmer getUserByEmail(String email);
    List<Farmer> getAllUsers();
    void deleteUser(String email);
    Boolean isUserExistByEmail(String email);
    Boolean isUserExistByUserid(String id);

    void submitForm(LoanForm loanForm, MultipartFile file, String fileName, int userId) throws IOException;
    Boolean isUserSubmittedForm(String email);
    LoanForm getLoanFormByEmail(String email);
    LoanForm updateLoanForm(LoanForm loanForm);
}
