package com.diploma.backend.service.impl;

import com.diploma.backend.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaMessageServiceImpl implements KafkaMessageService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    @Override
    public void sendMessage(String message) {
        String defaultTopic = kafkaTemplate.getDefaultTopic();
        kafkaTemplate.send(defaultTopic, message).addCallback(listenableFutureCallback(defaultTopic, message));
    }

    private <T> ListenableFutureCallback<T> listenableFutureCallback(String topic, T event) {
        return new ListenableFutureCallback<T>() {
            public void onFailure(Throwable e) {
                log.error("Failed to send event {} to topic {}. Exception: {}", event, topic, e);
            }

            public void onSuccess(T result) {
                log.trace("Event has sent successfully to topic {}, event: {}", topic, event);
            }
        };
    }
}
