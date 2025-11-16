package com.example.Action.Scheduler;


import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.Action.dto.JobDto;
import com.example.Action.model.Action;
import com.example.Action.model.RunDay;
import com.example.Action.service.ActionService;
import com.example.Action.service.KafkaProducerService;

import java.time.LocalDate;
import java.time.LocalDateTime;



@Component
public class ActionScheduler {
	
	@Autowired
	private ActionService service;
	
	@Autowired
    private KafkaProducerService jobProducer;
	
	// Runs every 30 minutes
    @Scheduled(fixedRate = 1800000)
    public void checkActions() {
        
    	LocalDate todayDate = LocalDate.now();
        RunDay todayRunDay = RunDay.valueOf(todayDate.getDayOfWeek().name());

//        // compute the scheduler time rounded to nearest half hour (floor)
//        LocalTime now = LocalTime.now();
//        LocalTime rounded = now.withSecond(0).withNano(0)
//            .withMinute(now.getMinute() < 30 ? 0 : 30);

        List<Action> candidates = service.getEnabledActions(); 
        for (Action action : candidates) {
        	// update lastRun timestamp
            action.setLastRun(LocalDateTime.now());
        	
            if (action.getRunOnDay() == RunDay.ALL || action.getRunOnDay() == todayRunDay) {
                LocalTime actionTime = action.getRunOnTime();
                if (actionTime != null && actionTime.equals(LocalTime.now())) {
                    // publish job
                    JobDto job = service.toJobDto(action);
                    jobProducer.publishJob(job);

                    

                    System.out.println("Published job for action: " + action.getName());
                }
            }
        }
    }
}