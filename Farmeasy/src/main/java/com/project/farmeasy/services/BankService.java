package com.project.farmeasy.services;

import com.project.farmeasy.entities.*;

import java.util.List;

public interface BankService {

    //void addBank(String email, String password, String role, int bankId);
    Scheme addScheme(Scheme scheme, Bank bank);
    List<Bank> getBanks();

    List<Scheme> getSchemes(String username);
    public List<Scheme> getSchemes();

    List<Apply> getApplies();
    List<Apply> getApplyByBank(String username);
    List<Apply> getApplyByFarmer(Farmer farmer);
    void updateApply(Apply apply, String status, String review);
    Apply getApply(Integer id);

}
