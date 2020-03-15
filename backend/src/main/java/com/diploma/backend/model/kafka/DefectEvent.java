package com.diploma.backend.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefectEvent implements KafkaMessage {
    private String code;
    private Integer projectId;
    private String description;
}
