package com.example.Security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Security.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByUserId(int userId);
}