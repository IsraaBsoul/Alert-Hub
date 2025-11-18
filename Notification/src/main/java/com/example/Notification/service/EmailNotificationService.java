package com.example.Notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Notification.dto.LogRequest;
import com.example.Notification.dto.JobDto;

import com.example.Notification.client.LoggerClient;

@Service
public class EmailNotificationService {
	
	@Autowired
	private LoggerClient loggerClient;

    public void sendEmail(JobDto job) {
        System.out.println("Sending Email to " + job.getToInfo() + ": " + job.getMessage());
        loggerClient.log(new LogRequest(
        	       "Email-Service",
        	       "INFO",
        	       "Email sent to " + job.getToInfo()
        	    ));
    }
}