package com.diploma.backend.config;

import com.diploma.backend.model.kafka.DefectEvent;
import com.diploma.backend.model.kafka.KafkaMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.server}")
    private String bootstrapAddress;
    @Value("${kafka.diploma.timeout}")
    private Integer timeout;

    @Bean
    public ProducerFactory<String, ? extends KafkaMessage> producerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, timeout);
        properties.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    @SuppressWarnings("unchecked")
    public KafkaTemplate<String, DefectEvent> kafkaTemplateDefect(@Value("${kafka.diploma.defects.topic}") String topic) {
        final KafkaTemplate<String, DefectEvent> kafkaTemplate =
                (KafkaTemplate<String, DefectEvent>) new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setDefaultTopic(topic);
        return kafkaTemplate;
    }

}
