package com.example.Action.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.Action.dto.JobDto;

@Service
public class KafkaProducerService {

	@Autowired
    private KafkaTemplate<String, JobDto> kafkaTemplate;
	
    private static final String TOPIC = "JobsQueue";

    

    public void publishJob(JobDto job) {
        kafkaTemplate.send(TOPIC, job);
        System.out.println("Published Job to Kafka: Name:" +job.getId());
    }

   
}