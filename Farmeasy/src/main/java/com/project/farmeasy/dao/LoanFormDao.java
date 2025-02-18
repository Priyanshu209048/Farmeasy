package com.project.farmeasy.dao;

import com.project.farmeasy.entities.LoanForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanFormDao extends JpaRepository<LoanForm, Integer> {
    Boolean existsByEmail(String email);
    LoanForm findByEmail(String email);
}
