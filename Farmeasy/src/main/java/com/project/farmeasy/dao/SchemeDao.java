package com.project.farmeasy.dao;

import com.project.farmeasy.entities.Bank;
import com.project.farmeasy.entities.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchemeDao extends JpaRepository<Scheme, Integer> {
    Scheme findByBank(Bank bank);
    List<Scheme> findAllByBank(Bank bank);
}
