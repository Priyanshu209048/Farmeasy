package com.project.farmeasy.dao;

import com.project.farmeasy.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {
}
