package com.example.Evaluation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Evaluation.client.LoaderClient;
import com.example.Evaluation.dto.NotificationDto;
import com.example.Evaluation.dto.TaskDto;

@Service
public class TotalTasksService {
	@Autowired
    private LoaderClient loaderClient;

    @Autowired
    private NotificationPublisher notificationPublisher;

    public int getTotalTasksForDeveloper(String developerId, int sinceDays, String requesterEmail) {
        LocalDateTime since = LocalDateTime.now().minusDays(sinceDays);

        List<TaskDto> tasks = loaderClient.getAllTasks();

        int totalTasks = 0;
        for (TaskDto task : tasks) {
            if (task.getDeveloperId() != null && task.getDeveloperId().equals(developerId)
                    && task.getTimestamp() != null
                    && task.getTimestamp().isAfter(since)) {
                totalTasks++;
            }
        }

        String message = "Developer '" + developerId + "' has " + totalTasks + " tasks in the last " + sinceDays + " days.";
      
        
        NotificationDto dto = new NotificationDto();
        dto.setMessage(message);
        dto.setToInfo(requesterEmail);
        notificationPublisher.publish(dto);
        
        return totalTasks;
    }
}
