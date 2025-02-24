package com.project.farmeasy.dao;

import com.project.farmeasy.entities.Apply;
import com.project.farmeasy.entities.Bank;
import com.project.farmeasy.entities.Grievences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrievencesDao extends JpaRepository<Grievences, Integer> {
    List<Grievences> findAllByBank(Bank bank);
}
