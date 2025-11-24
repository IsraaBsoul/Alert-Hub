package com.example.Evaluation.service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Evaluation.dto.NotificationDto;
@Component
public class NotificationPublisher {
	 @Autowired
	    private KafkaTemplate<String, NotificationDto> kafkaTemplate;

	    private static final String TOPIC = "EmailQueue";  

	    public void publish(NotificationDto message) {
	        kafkaTemplate.send(TOPIC, message);  
	    }
}
