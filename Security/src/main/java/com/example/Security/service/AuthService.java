package com.example.Security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Security.Client.UserClient;
import com.example.Security.dto.JwtResponse;
import com.example.Security.dto.SignInRequest;
import com.example.Security.dto.SignUpRequest;
import com.example.Security.dto.UserDto;
import com.example.Security.model.Role;
import com.example.Security.model.UserRole;
import com.example.Security.repository.RoleRepository;
import com.example.Security.repository.UserRoleRepository;


@Service
public class AuthService {


	@Autowired
    private UserRoleRepository userRoleRepository;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private JwtService jwtService;
//    @Autowired
//    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserClient userClient;
	


    public boolean hasPermission(int userId, String permission) {
        List<UserRole> roles = userRoleRepository.findByUserId(userId);
      
        
        // admin shortcut
        boolean isAdmin = roles.stream()
                .anyMatch(r -> r.getRole().getRoleName().equalsIgnoreCase("ADMIN"));

        if (isAdmin) return true;

        return roles.stream()
                .anyMatch(ur -> ur.getRole().getRoleName().equalsIgnoreCase(permission));
    }

    public void grantRole(int userId, String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        UserRole ur = new UserRole();
        ur.setUserId(userId);
        ur.setRole(role);
        userRoleRepository.save(ur);
    }

    public void revokeRole(int userId, String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        userRoleRepository.findByUserId(userId).stream()
        .filter(ur -> ur.getRole().getId() == role.getId())
        .forEach(userRoleRepository::delete);

    }

    // ---------- SIGN-UP ----------
    public String signUp(SignUpRequest request) {
        // Call User service to create user
        boolean exists = userClient.checkEmailExists(request.getEmail());
        if (exists) {
            throw new RuntimeException("Email already registered");
        }

        UserDto userDto = new UserDto();
        userDto.setEmail(request.getEmail());
        userDto.setPassword(passwordEncoder.encode(request.getPassword())); // store hashed
        userDto.setUsername(request.getUsername());
        userDto.setPhone(request.getPhone());

        UserDto createdUser = userClient.createUser(userDto);
        
        if (request.getUsername().equalsIgnoreCase("admin")) {
            grantRole(createdUser.getId(), "ADMIN");
        }
        else {
        	// Assign default "read" permission
            grantRole(createdUser.getId(), "read");
        	
        }

        return "User registered successfully!";
    }

    // ---------- SIGN-IN ----------
    public JwtResponse signIn(SignInRequest request) {
        // Authenticate via User service credentials
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//        );
    	UserDto user = userClient.getUserByEmail(request.getEmail());
    	if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    	    throw new RuntimeException("Invalid email or password");
    	}


//        UserDto user = userClient.getUserByEmail(request.getEmail());
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }

        String token = jwtService.generateToken(user);

        return new JwtResponse(token);
    }
}