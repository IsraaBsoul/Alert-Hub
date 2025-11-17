package com.example.Notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.Notification.dto.JobDto;

@Component
public class KafkaNotificationConsumer {
	 	@Autowired
	    private SmsNotificationService smsService;

	    @Autowired
	    private EmailNotificationService emailService;

	    @KafkaListener(topics = "SmsQueue", groupId = "notification-group")
	    public void consumeSms(JobDto job) {
	        System.out.println("Received SMS job: " + job.getName());
	        smsService.sendSms(job);
	    }

	    @KafkaListener(topics = "EmailQueue", groupId = "notification-group")
	    public void consumeEmail(JobDto job) {
	        System.out.println("Received Email job: " + job.getName());
	        emailService.sendEmail(job);
	    }
	    

}
