package com.project.farmeasy.dao;

import com.project.farmeasy.entities.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemeDao extends JpaRepository<Scheme, Integer> {
}
