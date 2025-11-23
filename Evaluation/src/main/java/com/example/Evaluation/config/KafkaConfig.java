package com.example.Evaluation.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.Evaluation.dto.ResponseDto;



@Configuration
public class KafkaConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers ;
	
	
	public Map<String , Object> producerConfig()
	{
		Map<String ,Object> props= new HashMap<String, Object>();
		props.put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
		return props;
	}
	
	@Bean
    public ProducerFactory<String, ResponseDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, ResponseDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
	

}
