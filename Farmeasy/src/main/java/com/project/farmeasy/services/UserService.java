package com.project.farmeasy.services;

import com.project.farmeasy.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User updateUser(String email, User user);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(String email);
    Boolean isUserExistByEmail(String email);
    Boolean isUserExistByUserid(String id);
}
