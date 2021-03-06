package com.diploma.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.diploma.backend.model.kafka.DefectEvent;
import com.diploma.backend.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaMessageServiceImpl implements KafkaMessageService {

    private final KafkaTemplate<String, DefectEvent> kafkaTemplateDefect;

    @Override
    public void sendDefectEvent(DefectEvent event) {
        String defaultTopic = kafkaTemplateDefect.getDefaultTopic();
        kafkaTemplateDefect.send(defaultTopic, event).addCallback(listenableFutureCallback(defaultTopic, event));
    }

    private <T> ListenableFutureCallback<T> listenableFutureCallback(String topic, T event) {
        return new ListenableFutureCallback<>() {
            public void onFailure(Throwable e) {
                log.error("Failed to send event {} to topic {}. Exception: {}", event, topic, e);
            }

            public void onSuccess(T result) {
                log.trace("Event has sent successfully to topic {}, event: {}", topic, event);
            }
        };
    }

}
