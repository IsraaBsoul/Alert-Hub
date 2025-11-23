package com.example.User.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "security", url = "http://localhost:8089/api/auth")
public interface PermissionClient {

    @GetMapping("/has-permission")
    boolean hasPermission(
            @RequestParam("userId") int userId,
            @RequestParam("permission") String permission
    );
}