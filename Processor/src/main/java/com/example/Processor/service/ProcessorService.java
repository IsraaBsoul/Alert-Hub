package com.example.Processor.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Processor.client.LoaderClient;
import com.example.Processor.client.MetricsClient;
import com.example.Processor.dto.JobDto;
import com.example.Processor.dto.MetricDto;
import com.example.Processor.dto.TaskDto;

@Service
public class ProcessorService {

	@Autowired
    private MetricsClient metricsClient;
	@Autowired
    private LoaderClient loaderClient;
	@Autowired
    private KafkaNotificationProducer notificationProducer;

    // Check if job conditions are satisfied
    public boolean isJobSatisfied(JobDto job) {

        List<TaskDto> allTasks = loaderClient.getAllTasks();

        for (List<Integer> group : job.getConditions()) { // OR groups
            boolean groupSatisfied = true; // AND inside group
            for (Integer metricId : group) {
                MetricDto metric = metricsClient.getMetricById(metricId);

                long count = allTasks.stream()
                        .filter(task -> task.getLabel().equalsIgnoreCase(metric.getLabel()))
                        .filter(task -> task.getTimestamp().isAfter(LocalDateTime.now().minusHours(metric.getTimeFrameHours())))
                        .count();

                if (count < metric.getThreshold()) {
                    groupSatisfied = false;
                    break; // AND fails
                }
            }
            if (groupSatisfied) return true; // OR succeeds
        }
        return false;
    }

    // Execute the job if conditions are satisfied
    public void executeJob(JobDto job) {
        System.out.println("Executing job: " + job.getName() + " → " + job.getActionType());
        notificationProducer.sendNotification(job);
    }
}