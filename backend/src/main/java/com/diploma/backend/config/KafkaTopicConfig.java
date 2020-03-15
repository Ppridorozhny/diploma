package com.diploma.backend.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.server}")
    private String bootstrapAddress;
    @Value("${kafka.diploma.timeout}")
    private Integer timeout;
    @Value("${kafka.diploma.number.partitions}")
    private Integer numberOfPartitions;
    @Value("${kafka.diploma.replication.factor}")
    private Short replicationFactor;

    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, timeout);
        return new KafkaAdmin(configs);
    }

    public NewTopic defects() {
        return new NewTopic("defects", numberOfPartitions, replicationFactor);
    }

}
