package com.example.Logger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Logger.model.LogEntry;
import com.example.Logger.repository.LogRepository;

@Service
public class LogService {

	@Autowired
	private LogRepository repository;
	
	public LogEntry saveLog(LogEntry log) {
        return repository.save(log);
    }
}
