package com.diploma.backend.service.impl;

import com.diploma.backend.model.kafka.DefectEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.KafkaMessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class KafkaEventListener {

    @Transactional
    @KafkaListener(topics = "${kafka.diploma.defects.topic}")
    public void listenDefectTopic(@Payload DefectEvent defectEvent, @Headers KafkaMessageHeaders headers) {
        log.debug("Proceed Defect event {} with headers {}", defectEvent, headers);
    }

}
