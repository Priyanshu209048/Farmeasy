package com.project.farmeasy.dao;

import com.project.farmeasy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, String> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
