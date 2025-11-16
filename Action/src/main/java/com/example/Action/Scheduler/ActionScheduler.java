package com.example.Action.Scheduler;


import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.Action.model.Action;
import com.example.Action.model.RunDay;
import com.example.Action.service.ActionService;
import java.time.LocalDate;
import java.time.LocalDateTime;



@Component
public class ActionScheduler {
	
	@Autowired
	private ActionService service;
	
    // runs every 30 minutes
    @Scheduled(fixedRate = 1800000)
    public void checkActions() {
        List<Action> actions = service.getAllActions();
        LocalTime now = LocalTime.now();
        
        // get today's day as RunDay enum
        RunDay today = RunDay.valueOf(LocalDate.now().getDayOfWeek().name());
        
        for (Action action : actions) {
            if (Boolean.TRUE.equals(action.getIsEnabled())
                    && !Boolean.TRUE.equals(action.getIsDeleted())
                    && (action.getRunOnDay() == RunDay.ALL || action.getRunOnDay() == today)
                    && action.getRunOnTime().equals(
                        now.withMinute(now.getMinute() < 30 ? 0 : 30)
                           .withSecond(0)
                           .withNano(0)
                    )) {

                System.out.println("Executing action: " + action.getName() + " Message: " + action.getMessage());
                action.setLastRun(LocalDateTime.now());
                service.saveAction(action);
            }
        }
    }
}