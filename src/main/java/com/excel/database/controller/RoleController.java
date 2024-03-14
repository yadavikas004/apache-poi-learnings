package com.excel.database.controller;

import com.excel.database.entity.Role;
import com.excel.database.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<String> addRole(@RequestBody Role role){
        try{
            roleService.addCourse(role);
            return ResponseEntity.status(HttpStatus.OK).body("Role had been added");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Roles not able to add!!!");
        }
    }
}
