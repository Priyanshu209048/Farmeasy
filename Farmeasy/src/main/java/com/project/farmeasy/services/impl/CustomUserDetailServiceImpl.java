package com.project.farmeasy.services.impl;

import com.project.farmeasy.dao.UserDao;
import com.project.farmeasy.config.CustomUserDetails;
import com.project.farmeasy.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        if (user == null)
            throw new UsernameNotFoundException("User not found with username: " + username);
        return new CustomUserDetails(user);
    }
}
