package com.example.Processor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.Processor.dto.JobDto;

@Service
public class KafkaJobConsumer {

	
		@Autowired
		private ProcessorService processorService;
		
		@KafkaListener(topics = "JobsQueue", groupId = "processor-group")
	    public void consumeJob(JobDto job) {
	        System.out.println("\n---- Job Received from Kafka ----");
	        System.out.println("ID: " + job.getId());
	        System.out.println("Name: " + job.getName());
	        System.out.println("Type: " + job.getActionType());
	        System.out.println("---------------------------------\n");

	        // Process the job
	        boolean satisfied = processorService.isJobSatisfied(job);
	        if (satisfied) {
	            processorService.executeJob(job);  // send SMS/email 
	        } else {
	            System.out.println("Job condition not satisfied: " + job.getName());
	        }
	    }
	}
