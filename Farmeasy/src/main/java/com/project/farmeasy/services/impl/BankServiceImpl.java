package com.project.farmeasy.services.impl;

import com.project.farmeasy.dao.ApplyDao;
import com.project.farmeasy.dao.BankDao;
import com.project.farmeasy.dao.SchemeDao;
import com.project.farmeasy.entities.Apply;
import com.project.farmeasy.entities.Bank;
import com.project.farmeasy.entities.Scheme;
import com.project.farmeasy.exceptions.ResourceNotFoundException;
import com.project.farmeasy.services.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final SchemeDao schemeDao;
    private final BankDao bankDao;
    private final ApplyDao applyDao;

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
    public List<Scheme> getSchemes() {
        return schemeDao.findAll();
    }

    @Override
    public List<Apply> getApplies() {
        return applyDao.findAll();
    }

    @Override
    public void updateApply(Apply apply, String status) {
        apply.setStatus(status);
        apply.setStatusDate(String.valueOf(LocalDate.now()));
        applyDao.save(apply);
    }

    @Override
    public Apply getApply(Integer id) {
        return applyDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("Apply", "id", String.valueOf(id)));
    }
}
