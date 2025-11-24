package com.example.Evaluation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;


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
import com.example.Evaluation.dto.TopDeveloperResponse;
import com.example.Evaluation.service.NotificationPublisher;
import com.example.Evaluation.service.TopDeveloperService;

public class TopDeveloperServiceTest {
	@Mock
    private LoaderClient loaderClient;

    @Mock
    private NotificationPublisher notificationPublisher;

    @InjectMocks
    private TopDeveloperService topDeveloperService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // modern replacement for deprecated initMocks
    }

    @Test
    void testGetTopDeveloperByLabel() {
        // Arrange
        TaskDto task1 = new TaskDto();
        task1.setDeveloperId("dev1");
        task1.setLabel("bug");
        task1.setTimestamp(LocalDateTime.now().minusDays(1));

        TaskDto task2 = new TaskDto();
        task2.setDeveloperId("dev2");
        task2.setLabel("bug");
        task2.setTimestamp(LocalDateTime.now().minusDays(1));

        TaskDto task3 = new TaskDto();
        task3.setDeveloperId("dev1");
        task3.setLabel("bug");
        task3.setTimestamp(LocalDateTime.now().minusDays(1));

        List<TaskDto> tasks = Arrays.asList(task1, task2, task3);

        when(loaderClient.getAllTasks()).thenReturn(tasks);
//
//        TopDeveloperResponse response = topDeveloperService.getTopDeveloperByLabel("bug", 7);
//
//        // Assert
//        assertEquals("dev1", response.getDeveloperId());
//        assertEquals("bug", response.getLabel());
//        assertEquals(2, response.getTaskCount());

        // Verify notification was sent
//        verify(notificationPublisher, times(1))
//        .publishEmail(anyString());

    }
}
