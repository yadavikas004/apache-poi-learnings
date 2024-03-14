package com.excel.database.service;

import com.excel.database.entity.Role;

import java.util.Set;

public interface RoleService {

    public void addCourse(Role role);

    public Set<Role> getAllRolesByEmail(String email);
}
