package com.example.Notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Notification.dto.LogRequest;

@FeignClient(name = "Logger", url = "http://localhost:8086/api/logs")
public interface LoggerClient {

    @PostMapping
    void log(@RequestBody LogRequest log);
}
