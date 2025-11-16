package com.example.Action.service;

import org.springframework.stereotype.Service;

import com.example.Action.dto.JobDto;
import com.example.Action.model.Action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class KafkaProducerService {
	
    private final String TOPIC = "JobsQueue";
	
	@Autowired 
	private KafkaTemplate<String, JobDto> kafkaTemplate;
	
	@Autowired
    private ActionService actionService;
	
	public void publishJob(JobDto job) {

        // check if actionId is valid (enabled + not deleted)
        boolean valid = actionService.isActionValid(job.getActionId());

        if (valid) {
        	
            kafkaTemplate.send(TOPIC, job.getActionId().toString(), job);
            Action action = actionService.getActionById(job.getActionId());

            // Mark as deleted
            action.setIsDeleted(true);
            actionService.updateAction(job.getActionId(),action);
            
        } else {
            System.out.println("Action " + job.getActionId() + " is NOT valid — skipping publish.");
        }
    }
	
    public int publishAll() {

        List<Action> actions = actionService.getEnabledActions();
        int count = 0;

        for (Action a : actions) {
            JobDto job = actionService.toJobDto(a);  
            publishJob(job);
            count++;
        }

        return count;
    }

}
