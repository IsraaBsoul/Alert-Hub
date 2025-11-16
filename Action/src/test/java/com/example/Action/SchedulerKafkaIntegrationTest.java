package com.example.Action;

import com.example.Action.Scheduler.ActionScheduler;
import com.example.Action.model.Action;
import com.example.Action.model.RunDay;
import com.example.Action.service.ActionService;
import com.example.Action.dto.JobDto;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "JobsQueue" })
public class SchedulerKafkaIntegrationTest {

    

    @Autowired
    private ConsumerFactory<String, JobDto> consumerFactory;

    @Autowired
    private ActionScheduler scheduler;

    @Autowired
    private ActionService actionService;

    private Action action;

//    @BeforeEach
//    void setup() {
//        action = new Action();
//        action.setId(UUID.randomUUID());
//        action.setName("IntegrationTestAction");
//        action.setIsEnabled(true);
//        action.setIsDeleted(false);
//        action.setRunOnDay(RunDay.ALL);
//        action.setRunOnTime(LocalTime.now());
//
//        // save action to DB (H2 or real repo)
//        actionService.saveAction(action);
//    }
    
    @BeforeEach
    void setup() {
        action = new Action();
        action.setId(UUID.randomUUID());
        action.setName("IntegrationTestAction");
        action.setIsEnabled(true);
        action.setIsDeleted(false);
        action.setRunOnDay(RunDay.ALL);
        action.setRunOnTime(LocalTime.of(12, 0)); // fixed time

        // persist entity and use the managed instance
        action = actionService.saveAction(action);
    }


    @Test
    void testSchedulerPublishesJobToKafka() {
        // Call scheduler directly
        scheduler.checkActions();

        // create a consumer to read from embedded Kafka
        Consumer<String, JobDto> consumer = consumerFactory.createConsumer();
        consumer.subscribe(java.util.List.of("JobsQueue"));

        ConsumerRecords<String, JobDto> records = KafkaTestUtils.getRecords(consumer);

        assertTrue(records.count() > 0, "Kafka should have received at least 1 record");

        consumer.close();
    }
}
