package com.example.Notification.config;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.Notification.dto.NotificationDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
	
    private final String groupId = "notification-group"; // must match the @KafkaListener groupId

    @Bean
    public ConsumerFactory<String, NotificationDto> jobConsumerFactory() {
        // Configure JSON deserializer
        JsonDeserializer<NotificationDto> deserializer = new JsonDeserializer<>(NotificationDto.class);
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
    public ConcurrentKafkaListenerContainerFactory<String, NotificationDto> jobKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NotificationDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(jobConsumerFactory());
        return factory;
    }
}
