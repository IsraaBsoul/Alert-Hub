package com.example.Notification.service;

import org.springframework.stereotype.Service;

import com.example.Notification.dto.JobDto;

@Service
public class EmailNotificationService {

    public void sendEmail(JobDto job) {
        System.out.println("Sending Email to " + job.getToInfo() + ": " + job.getMessage());
    }
}