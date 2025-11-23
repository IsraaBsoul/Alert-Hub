package com.example.User.util;

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
}