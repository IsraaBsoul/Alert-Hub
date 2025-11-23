package com.example.Action.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

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