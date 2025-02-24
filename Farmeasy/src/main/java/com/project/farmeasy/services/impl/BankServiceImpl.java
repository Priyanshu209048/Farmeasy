package com.project.farmeasy.services.impl;

import com.project.farmeasy.dao.ApplyDao;
import com.project.farmeasy.dao.BankDao;
import com.project.farmeasy.dao.GrievencesDao;
import com.project.farmeasy.dao.SchemeDao;
import com.project.farmeasy.entities.*;
import com.project.farmeasy.exceptions.ResourceNotFoundException;
import com.project.farmeasy.services.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final SchemeDao schemeDao;
    private final BankDao bankDao;
    private final ApplyDao applyDao;
    private final GrievencesDao grievencesDao;

    @Override
    public Scheme addScheme(Scheme scheme, Bank bank) {
        System.out.println("Start");
        scheme.setBank(bank);
        System.out.println("End");
        return schemeDao.save(scheme);
    }

    @Override
    public List<Bank> getBanks() {
        return bankDao.findAll();
    }

    @Override
    public List<Scheme> getSchemes(String username) {
        return schemeDao.findAllByBank(bankDao.findByEmail(username));
    }

    @Override
    public List<Scheme> getSchemes() {
        return schemeDao.findAll();
    }

    @Override
    public List<Apply> getApplies() {
        return applyDao.findAll();
    }

    @Override
    public List<Apply> getApplyByBank(String username) {
        System.out.println(username);
        return applyDao.findAllByBank(bankDao.findByEmail(username));
    }

    @Override
    public List<Apply> getApplyByFarmer(Farmer farmer) {
        return applyDao.findAllByFarmer(farmer);
    }

    @Override
    public void updateApply(Apply apply, String status, String review) {
        apply.setStatus(status);
        apply.setReview(review);
        apply.setStatusDate(String.valueOf(LocalDate.now()));
        applyDao.save(apply);
    }

    @Override
    public Apply getApply(Integer id) {
        return applyDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("Apply", "id", String.valueOf(id)));
    }
}
