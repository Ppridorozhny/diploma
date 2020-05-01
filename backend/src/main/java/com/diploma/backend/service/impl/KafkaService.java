package com.diploma.backend.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsOptions;
import org.apache.kafka.clients.admin.DeleteTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaService {

    private static final String RETENTION = "retention.ms";

    private final KafkaAdmin kafkaAdmin;

    @Value("${kafka.diploma.number.partitions}")
    private Integer numberOfPartitions;
    @Value("${kafka.diploma.replication.factor}")
    private Short replicationFactor;

    public boolean createTopic(String topicName, Long retention) {
        try (AdminClient client = AdminClient.create(kafkaAdmin.getConfig())) {
            if (!isTopicExists(topicName, client)) {
                Map<String, String> config = new HashMap<>();
                Optional.ofNullable(retention).ifPresent(r -> config.put(RETENTION, String.valueOf(r)));
                List<NewTopic> topicList = List.of(new NewTopic(topicName, numberOfPartitions, replicationFactor)
                        .configs(config));
                client.createTopics(topicList, new CreateTopicsOptions()).all().get();
                return true;
            }
            return false;
        } catch (InterruptedException | ExecutionException e) {
            throw new KafkaException(e);
        }
    }

    public boolean deleteTopic(String topicName) throws InterruptedException, ExecutionException {
        try (AdminClient client = AdminClient.create(kafkaAdmin.getConfig())) {
            if (isTopicExists(topicName, client)) {
                client.deleteTopics(Collections.singleton(topicName), new DeleteTopicsOptions())
                        .all()
                        .get();
                return true;
            }
            return false;
        } catch (InterruptedException | ExecutionException e) {
            log.error("Unable to delete topic: {}", topicName);
            throw e;
        }
    }

    private boolean isTopicExists(String topicName, AdminClient client) {
        ListTopicsOptions listOptions = new ListTopicsOptions();
        KafkaFuture<Set<String>> listResult = client.listTopics(listOptions).names();
        try {
            Set<String> topicNamesSet = listResult.get();
            return topicNamesSet.contains(topicName);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Unable to check topic for existence: {}", topicName);
            throw new KafkaException(e);
        }
    }

}
