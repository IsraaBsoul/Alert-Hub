package com.example.Notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.Notification.dto.LogRequest;
import com.example.Notification.dto.JobDto;

import com.example.Notification.client.LoggerClient;

@Service
public class EmailNotificationService {
	
	@Autowired
	private LoggerClient loggerClient;
	@Autowired
    private JavaMailSender mailSender;

    public void sendEmail(JobDto job) {
    	SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("nmn01927@gmail.com"); 
        mail.setTo(job.getToInfo());         
        mail.setSubject("Alert Hub Notification"); 
        mail.setText(job.getMessage());       
        
        loggerClient.log(new LogRequest(
        	       "Email-Service",
        	       "INFO",
        	       "Email sent to " + job.getToInfo()
        	    ));
        
        mailSender.send(mail);
        System.out.println("Email sent to " + job.getToInfo());
    }
}
