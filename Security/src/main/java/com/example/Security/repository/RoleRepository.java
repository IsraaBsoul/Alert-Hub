package com.example.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Security.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}