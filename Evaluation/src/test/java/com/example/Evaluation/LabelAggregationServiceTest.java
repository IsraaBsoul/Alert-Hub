package com.example.Evaluation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Evaluation.client.LoaderClient;
import com.example.Evaluation.dto.TaskDto;
import com.example.Evaluation.service.LabelAggregationService;
import com.example.Evaluation.service.NotificationPublisher;

public class LabelAggregationServiceTest {
    
    @Mock
    private LoaderClient loaderClient; // Mock the LoaderClient

    @Mock
    private NotificationPublisher notificationPublisher; // Mock the NotificationPublisher

    @InjectMocks
    private LabelAggregationService labelAggregationService; // Inject mocks into the service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testGetLabelAggregationForDeveloper() {
        // Arrange
        TaskDto task1 = new TaskDto();
        task1.setDeveloperId("dev1");
        task1.setLabel("bug");
        task1.setTimestamp(LocalDateTime.now().minusDays(1));

        TaskDto task2 = new TaskDto();
        task2.setDeveloperId("dev1");
        task2.setLabel("feature");
        task2.setTimestamp(LocalDateTime.now().minusDays(1));

        TaskDto task3 = new TaskDto();
        task3.setDeveloperId("dev1");
        task3.setLabel("bug");
        task3.setTimestamp(LocalDateTime.now().minusDays(2));

        TaskDto task4 = new TaskDto();
        task4.setDeveloperId("dev2");
        task4.setLabel("bug");
        task4.setTimestamp(LocalDateTime.now().minusDays(1));

        List<TaskDto> tasks = Arrays.asList(task1, task2, task3, task4);

        when(loaderClient.getAllTasks()).thenReturn(tasks); // Mock the return value of getAllTasks

        // Act
//        Map<String, Integer> result = labelAggregationService.getLabelAggregationForDeveloper("dev1", 7);

        // Assert
//        assertEquals(2, result.get("bug"));
//        assertEquals(1, result.get("feature"));
//        assertEquals(2, result.size());

        // Verify that the notificationPublisher's publishEmail method was called exactly once
//        verify(notificationPublisher, times(1)).publishEmail(anyString());
    }
}
