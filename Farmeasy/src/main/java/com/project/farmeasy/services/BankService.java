package com.project.farmeasy.services;

import com.project.farmeasy.entities.Bank;
import com.project.farmeasy.entities.Scheme;

import java.util.List;

public interface BankService {

    //void addBank(String email, String password, String role, int bankId);
    Scheme addScheme(Scheme scheme, Bank bank);
    List<Bank> getBanks();
    List<Scheme> getSchemes();

}
