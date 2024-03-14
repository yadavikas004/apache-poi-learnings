package com.excel.database.repo;

import com.excel.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findByUsers_Email(String email);
}
