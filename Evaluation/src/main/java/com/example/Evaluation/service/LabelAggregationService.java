package com.example.Evaluation.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Evaluation.client.LoaderClient;
import com.example.Evaluation.dto.TaskDto;

@Service
public class LabelAggregationService {
		@Autowired
	    private LoaderClient loaderClient;

	    @Autowired
	    private NotificationPublisher notificationPublisher;

	    
	    public Map<String, Integer> getLabelAggregationForDeveloper(String developerId, int sinceDays) {
	        LocalDateTime since = LocalDateTime.now().minusDays(sinceDays);

	        // Fetch all tasks from loader
	        List<TaskDto> tasks = loaderClient.getAllTasks();

	        // Count tasks per label for the specified developer
	        Map<String, Integer> labelCountMap = new HashMap<>();
	        for (TaskDto task : tasks) {
	            if (task.getDeveloperId() != null && task.getDeveloperId().equals(developerId)
	                    && task.getLabel() != null
	                    && task.getTimestamp() != null
	                    && task.getTimestamp().isAfter(since)) {

	                labelCountMap.put(task.getLabel(), labelCountMap.getOrDefault(task.getLabel(), 0) + 1);
	            }
	        }

	        String message = "Developer '" + developerId + "' label aggregation in last " + sinceDays + " days: " + labelCountMap;
	        notificationPublisher.publishEmail(message);

	        return labelCountMap;
	    }
	
}
