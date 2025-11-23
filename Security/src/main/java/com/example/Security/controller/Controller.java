
package com.example.Security.controller;

import com.example.Security.dto.JwtResponse;
import com.example.Security.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import com.example.Security.dto.SignInRequest;
import com.example.Security.dto.SignUpRequest;
import com.example.Security.service.AuthService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class Controller {

	@Autowired
    private AuthService authService;
	@Autowired
    private JwtUtil jwtUtil;


    // ---------------- SIGN-UP ----------------
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
        String result = authService.signUp(request);
        return ResponseEntity.ok(result);
    }

    // ---------------- SIGN-IN ----------------
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signIn(@RequestBody SignInRequest request) {
        JwtResponse jwt = authService.signIn(request);
        return ResponseEntity.ok(jwt);
    }

    // ---------------- GRANT ROLE ----------------
//    @PostMapping("/grant-role")
//    public ResponseEntity<String> grantRole(@RequestParam int userId,
//                                            @RequestParam String roleName) {
//        authService.grantRole(userId, roleName);
//        return ResponseEntity.ok("Role granted successfully");
//    }
    @PostMapping("/grant-role")
    public ResponseEntity<String> grantRole(
            @RequestParam int userId,
            @RequestParam String roleName,
            HttpServletRequest request) {

        int requesterId = jwtUtil.extractUserId(request);

        // Only admin can do this
        if (!authService.hasPermission(requesterId, "admin")) {
            return ResponseEntity.status(403).body("Forbidden - Admin only");
        }

        authService.grantRole(userId, roleName);
        return ResponseEntity.ok("Role granted successfully");
    }


    // ---------------- REVOKE ROLE ----------------
    @PostMapping("/revoke-role")
    public ResponseEntity<String> revokeRole(
            @RequestParam int userId,
            @RequestParam String roleName,
            HttpServletRequest request) {

        int requesterId = jwtUtil.extractUserId(request);

        // Only admin can do this
        if (!authService.hasPermission(requesterId, "ADMIN")) {
            return ResponseEntity.status(403).body("Forbidden - Admin only");
        }

        authService.revokeRole(userId, roleName);
        return ResponseEntity.ok("Role revoked successfully");
    }

    // ---------------- CHECK PERMISSION ----------------
    @GetMapping("/has-permission")
    public ResponseEntity<Boolean> hasPermission(@RequestParam int userId,
                                                 @RequestParam String permission) {
        boolean hasPerm = authService.hasPermission(userId, permission);
        return ResponseEntity.ok(hasPerm);
    }
}
