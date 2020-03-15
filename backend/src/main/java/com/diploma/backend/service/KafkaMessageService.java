package com.diploma.backend.service;

import com.diploma.backend.model.kafka.DefectEvent;

public interface KafkaMessageService {
    void sendDefectEvent(DefectEvent event);
}
