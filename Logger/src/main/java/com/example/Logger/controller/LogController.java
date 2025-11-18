package com.example.Logger.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Logger.model.LogEntry;
import com.example.Logger.service.LogService;

@RestController
@RequestMapping("/api/logs")
public class LogController {
	
	@Autowired
	private LogService service;
	
	@PostMapping
    public LogEntry saveLog(@RequestBody LogEntry logRequest) {
        logRequest.setTimestamp(LocalDateTime.now());
        return service.saveLog(logRequest);
    }

}
