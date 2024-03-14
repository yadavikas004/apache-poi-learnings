package com.excel.database.service;

import com.excel.database.entity.Role;
import com.excel.database.repo.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public void addCourse(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Set<Role> getAllRolesByEmail(String email) {
        List<Role> roles = roleRepository.findByUsers_Email(email);
        return new HashSet<>(roles);
    }
}
