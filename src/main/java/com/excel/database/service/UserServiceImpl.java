package com.excel.database.service;

import com.excel.database.entity.User;
import com.excel.database.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public User authenticate(String email, String password) {
        // Find user by email
        User user = userRepository.findByEmail(email);

        // Check if user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null; // Authentication failed
        }
    }
}
