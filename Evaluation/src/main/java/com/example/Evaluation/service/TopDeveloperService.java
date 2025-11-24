package com.example.Evaluation.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Evaluation.client.LoaderClient;
import com.example.Evaluation.dto.NotificationDto;
import com.example.Evaluation.dto.TaskDto;
import com.example.Evaluation.dto.TopDeveloperResponse;
import com.example.Evaluation.exception.LoaderServiceException;
import com.example.Evaluation.exception.DeveloperNotFoundException;
import com.example.Evaluation.exception.NotificationException;

@Service
public class TopDeveloperService {

    @Autowired
    private LoaderClient loaderClient;

    @Autowired
    private NotificationPublisher notificationPublisher;

    public TopDeveloperResponse getTopDeveloperByLabel(String label, int sinceDays,String requesterEmail) {

        LocalDateTime since = LocalDateTime.now().minusDays(sinceDays);
        List<TaskDto> tasks;

        try {
            tasks = loaderClient.getAllTasks();
        } catch (Exception ex) {
            throw new LoaderServiceException("Failed to contact Loader service", ex);
        }

        Map<String, Integer> developerCountMap = new HashMap<>();

        for (TaskDto task : tasks) {
            if (task.getLabel() != null && task.getLabel().equals(label)
                    && task.getTimestamp() != null
                    && task.getTimestamp().isAfter(since)) {

                String developerId = task.getDeveloperId();
                if (developerId != null) {
                    developerCountMap.put(developerId,
                            developerCountMap.getOrDefault(developerId, 0) + 1);
                }
            }
        }

        if (developerCountMap.isEmpty()) {
            throw new DeveloperNotFoundException("No developers found for label: " + label);
        }

        String topDeveloperId = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : developerCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                topDeveloperId = entry.getKey();
            }
        }

        TopDeveloperResponse response = new TopDeveloperResponse(topDeveloperId,label,maxCount);

        String message = "Top developer for label '" + label + "' is " +
                topDeveloperId + " with " + maxCount + " tasks.";
        
        
        NotificationDto dto = new NotificationDto();
        dto.setMessage(message);
        dto.setToInfo(requesterEmail);
        notificationPublisher.publish(dto);
        


        return response;
    }
}
