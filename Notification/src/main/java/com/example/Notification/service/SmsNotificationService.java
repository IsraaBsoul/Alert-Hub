package com.example.Notification.service;

import org.springframework.stereotype.Service;

import com.example.Notification.dto.JobDto;

@Service
public class SmsNotificationService {

    public void sendSms(JobDto job) {
        System.out.println("Sending SMS to " + job.getToInfo() + ": " + job.getMessage());
    }

}
