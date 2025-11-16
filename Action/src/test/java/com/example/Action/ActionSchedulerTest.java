package com.example.Action;

import com.example.Action.Scheduler.ActionScheduler;
import com.example.Action.model.Action;
import com.example.Action.model.RunDay;
import com.example.Action.service.ActionService;
import com.example.Action.service.KafkaProducerService;
import com.example.Action.dto.JobDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ActionSchedulerTest {

    @Mock
    private ActionService actionService;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private ActionScheduler scheduler;

    private Action action;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        action = new Action();
        action.setId(UUID.randomUUID());
        action.setName("TestAction");
        action.setIsEnabled(true);
        action.setIsDeleted(false);
        action.setRunOnDay(RunDay.ALL);
        action.setRunOnTime(LocalTime.of(12, 0)); // fixed time
    }

    @Test
    void testSchedulerPublishesJob() {
        // Mock the service to return our action
        when(actionService.getEnabledActions()).thenReturn(List.of(action));
        when(actionService.toJobDto(action)).thenReturn(new JobDto());

        // Mock LocalTime.now() to match action.getRunOnTime()
        try (MockedStatic<LocalTime> mockedTime = mockStatic(LocalTime.class)) {
            mockedTime.when(LocalTime::now).thenReturn(action.getRunOnTime());

            // Call the scheduler method directly
            scheduler.checkActions();
        }

        // Verify Kafka producer was called
        verify(kafkaProducerService, times(1)).publishJob(any(JobDto.class));

        // Verify lastRun timestamp was updated
        assert(action.getLastRun() != null);
    }
}
