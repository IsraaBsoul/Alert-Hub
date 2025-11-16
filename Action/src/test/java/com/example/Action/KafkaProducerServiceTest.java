package com.example.Action;

import static org.mockito.Mockito.*;

import com.example.Action.dto.JobDto;
import com.example.Action.model.Action;
import com.example.Action.service.ActionService;
import com.example.Action.service.KafkaProducerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

public class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, JobDto> kafkaTemplate;

    @Mock
    private ActionService actionService;

    @InjectMocks
    private KafkaProducerService producerService;

    private Action action;
    private JobDto jobDto;
    private UUID actionId;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        actionId = UUID.randomUUID(); // ONE UUID instance

        action = new Action();
        action.setId(actionId);
        action.setIsEnabled(true);
        action.setIsDeleted(false);
        action.setName("TestAction");

        jobDto = new JobDto();
        jobDto.setActionId(actionId); // ✅ SAME UUID instance
    }


    @Test
    void testPublishJob_validAction_marksDeleted() {
        // Mock the behavior
        when(actionService.isActionValid(actionId)).thenReturn(true);
        when(actionService.getActionById(actionId)).thenReturn(action);

        // Call the method
        producerService.publishJob(jobDto);

        // Verify Kafka was called
        verify(kafkaTemplate, times(1))
                .send(eq("JobsQueue"), eq(actionId.toString()), eq(jobDto));

        // Verify Action is updated as deleted
        verify(actionService, times(1))
                .updateAction(eq(actionId), argThat(a -> a.getIsDeleted() == true));
    }

    @Test
    void testPublishJob_invalidAction_skipsPublish() {
        when(actionService.isActionValid(actionId)).thenReturn(false);

        producerService.publishJob(jobDto);

        // Kafka should not be called
        verify(kafkaTemplate, never()).send(anyString(), anyString(), any());

        // updateAction should not be called
        verify(actionService, never()).updateAction(any(), any());
    }
}
