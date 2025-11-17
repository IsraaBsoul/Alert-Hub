package com.example.Processor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.Processor.dto.JobDto;

@Service
public class KafkaNotificationProducer {

	@Autowired
    private KafkaTemplate<String, JobDto> kafkaTemplate;

    private static final String SMS_TOPIC = "SmsQueue";
    private static final String EMAIL_TOPIC = "EmailQueue";

    public void sendNotification(JobDto job) {
        if ("SMS".equalsIgnoreCase(job.getActionType())) {
            kafkaTemplate.send(SMS_TOPIC, job);
            System.out.println("Sent SMS job to Kafka: " + job.getName());
        } else if ("EMAIL".equalsIgnoreCase(job.getActionType())) {
            kafkaTemplate.send(EMAIL_TOPIC, job);
            System.out.println("Sent Email job to Kafka: " + job.getName());
        } else {
            System.out.println("Unknown action type: " + job.getActionType());
        }
    }
}