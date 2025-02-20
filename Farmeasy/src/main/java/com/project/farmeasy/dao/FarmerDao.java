package com.project.farmeasy.dao;

import com.project.farmeasy.entities.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmerDao extends JpaRepository<Farmer, String> {
    Boolean existsByEmail(String email);
    Optional<Farmer> findByEmail(String email);
}
