package com.project.farmeasy.dao;

import com.project.farmeasy.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDao extends JpaRepository<Bank, Integer> {
}
