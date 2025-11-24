package com.example.Evaluation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.times;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Evaluation.client.LoaderClient;
import com.example.Evaluation.dto.TaskDto;
import com.example.Evaluation.service.NotificationPublisher;
import com.example.Evaluation.service.TotalTasksService;

public class TotalTasksServiceTest {

    @Mock
    private LoaderClient loaderClient; 

    @Mock
    private NotificationPublisher notificationPublisher; 

    @InjectMocks
    private TotalTasksService totalTasksService; 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    void testGetTotalTasksForDeveloper() {
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
        task3.setDeveloperId("dev2");
        task3.setLabel("bug");
        task3.setTimestamp(LocalDateTime.now().minusDays(1));

        List<TaskDto> tasks = Arrays.asList(task1, task2, task3);

        when(loaderClient.getAllTasks()).thenReturn(tasks); 

//        int totalTasks = totalTasksService.getTotalTasksForDeveloper("dev1", 7);

//        assertEquals(2, totalTasks); 

//        verify(notificationPublisher, times(1)).publishEmail(anyString());
    }
}
