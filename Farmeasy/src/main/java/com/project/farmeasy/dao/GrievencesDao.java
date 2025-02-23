package com.project.farmeasy.dao;

import com.project.farmeasy.entities.Grievences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrievencesDao extends JpaRepository<Grievences, Integer> {
}
