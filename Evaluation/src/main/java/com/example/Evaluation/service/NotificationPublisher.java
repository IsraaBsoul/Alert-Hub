package com.example.Evaluation.service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class NotificationPublisher {
	 @Autowired
	    private KafkaTemplate<String, String> kafkaTemplate;

	    private static final String TOPIC = "email-notifications";  

	    public void publishEmail(String message) {
	        kafkaTemplate.send(TOPIC, message);  
	    }
}
