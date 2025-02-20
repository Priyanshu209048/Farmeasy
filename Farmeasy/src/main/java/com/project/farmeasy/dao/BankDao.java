package com.project.farmeasy.dao;

import com.project.farmeasy.entities.Bank;
import com.project.farmeasy.entities.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankDao extends JpaRepository<Bank, Integer> {
    Bank findByEmail(String email);
}
