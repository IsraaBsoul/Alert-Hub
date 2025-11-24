package com.example.Evaluation.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;


@Component
public class JwtUserContext {

    public static int getUserId(HttpServletRequest request) {
        String header = request.getHeader("X-USER-ID");
        if (header == null) {
            throw new RuntimeException("Missing X-USER-ID header");
        }
        return Integer.parseInt(header);
    }
    
    public static String getUserEmail(HttpServletRequest request) {
        String email = request.getHeader("X-USER-EMAIL");
        if (email == null) {
            throw new RuntimeException("Missing X-USER-EMAIL header");
        }
        return email;
    }
}