package com.project.farmeasy.dao;

import com.project.farmeasy.entities.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyDao extends JpaRepository<Apply, Integer> {
}
