package com.excel.database.service;

import com.excel.database.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User authenticate(String email, String password);
}
