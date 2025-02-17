package com.project.farmeasy.services.impl;

import com.project.farmeasy.dao.UserDao;
import com.project.farmeasy.entities.User;
import com.project.farmeasy.exceptions.ResourceNotFoundException;
import com.project.farmeasy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) {
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        user.setId(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_FARMER");
        return userDao.save(user);
    }

    @Override
    public User updateUser(String email, User user) {
        User update = userDao.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User", "email", email));
        update.setFirstName(user.getFirstName());
        update.setLastName(user.getLastName());
        update.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        update.setContact(user.getContact());
        return userDao.save(update);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void deleteUser(String email) {
        User user = userDao.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User", "email", email));
        userDao.delete(user);
    }

    @Override
    public Boolean isUserExistByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public Boolean isUserExistByUserid(String id) {
        return userDao.existsById(id);
    }
}
