package com.example.Notification.config;


import com.example.Notification.dto.JobDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private final String bootstrapServers = "localhost:9092"; // or use @Value("${spring.kafka.bootstrap-servers}")

    private final String groupId = "notification-group"; // must match the @KafkaListener groupId

    @Bean
    public ConsumerFactory<String, JobDto> jobConsumerFactory() {
        // Configure JSON deserializer
        JsonDeserializer<JobDto> deserializer = new JsonDeserializer<>(JobDto.class);
        deserializer.addTrustedPackages("*"); // trust your DTO package
        deserializer.setRemoveTypeHeaders(false); // optional
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // start from the beginning for testing

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JobDto> jobKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, JobDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(jobConsumerFactory());
        return factory;
    }
}
