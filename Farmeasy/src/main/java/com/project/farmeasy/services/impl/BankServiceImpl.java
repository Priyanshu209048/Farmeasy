package com.project.farmeasy.services.impl;

import com.project.farmeasy.dao.BankDao;
import com.project.farmeasy.dao.SchemeDao;
import com.project.farmeasy.entities.Bank;
import com.project.farmeasy.entities.Scheme;
import com.project.farmeasy.services.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final SchemeDao schemeDao;
    private final BankDao bankDao;

    @Override
    public Scheme addScheme(Scheme scheme, Bank bank) {
        scheme.setBank(bank);
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
}
