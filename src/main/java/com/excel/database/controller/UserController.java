package com.excel.database.controller;

import com.excel.database.entity.Role;
import com.excel.database.entity.User;
import com.excel.database.jwt.JwtRequest;
import com.excel.database.jwt.JwtResponse;
import com.excel.database.service.RoleService;
import com.excel.database.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private  RoleService roleService;

    @Autowired
    private  UserService userService;

//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request, HttpSession session){
        System.out.println(request);
        // Authenticate user
        User user = userService.authenticate(request.getEmail(), request.getPassword());
        if (user != null) {
            // Fetch roles for the authenticated user
            Set<Role> roles = roleService.getAllRolesByEmail(user.getEmail());

            // Create JwtResponse containing token and roles
            JwtResponse response = new JwtResponse(roles);

            // Store JwtResponse in session
            session.setAttribute("jwtResponse", response);

            // Return response with JWT token and roles
            return ResponseEntity.ok(response);
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
