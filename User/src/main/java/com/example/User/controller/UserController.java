package com.example.User.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.User.model.User;
import com.example.User.service.UserService;
import com.example.User.util.JwtUserContext;
import com.example.User.util.PermissionClient;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private PermissionClient permissionClient;

    // Admin-only user creation
    @PostMapping
    public ResponseEntity<?> createUserByAdmin(@RequestBody User user, HttpServletRequest request) {
        int requesterId = JwtUserContext.getUserId(request);

        // Admin check
        boolean allowed = permissionClient.hasPermission(requesterId, "admin");
        if (!allowed) {
            return ResponseEntity.status(403).body("Forbidden - Admin only");
        }

        return ResponseEntity.ok(service.createUser(user));
    }

    // Create
    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    // Read all
    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return service.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        try {
            return ResponseEntity.ok(service.updateUser(id, user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    // Delete - admin only
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserByAdmin(@PathVariable int id, HttpServletRequest request) {
        int requesterId = JwtUserContext.getUserId(request);

        boolean allowed = permissionClient.hasPermission(requesterId, "admin");
        if (!allowed) {
            return ResponseEntity.status(403).body("Forbidden - Admin only");
        }

        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
 // Get user by email
    @GetMapping("/by-email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return service.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Check if email exists
    @GetMapping("/exists")
    public boolean checkEmailExists(@RequestParam String email) {
        return service.existsByEmail(email);
    }

}