

package com.example.Security.util;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Security.service.JwtService;

@Component
public class JwtUtil {
	@Autowired
    private JwtService jwtService;

    // Get token from Authorization header
    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
    
    public Integer extractUserId(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) return null;
        return jwtService.extractUserId(token);
    }
}
