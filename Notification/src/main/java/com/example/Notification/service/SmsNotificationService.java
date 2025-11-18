package com.example.Notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Notification.client.LoggerClient;
import com.example.Notification.dto.JobDto;
import com.example.Notification.dto.LogRequest;

@Service
public class SmsNotificationService {
	
	@Autowired
	private LoggerClient loggerClient;
 
    public void sendSms(JobDto job) {
        System.out.println("Sending SMS to " + job.getToInfo() + ": " + job.getMessage());
        loggerClient.log(new LogRequest(
        	       "SMS-Service",
        	       "INFO",
        	       "SMS sent to " + job.getToInfo()
        	    ));
    }

}
