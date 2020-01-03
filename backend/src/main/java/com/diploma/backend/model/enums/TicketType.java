package com.diploma.backend.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TicketType {

    DEFECT("Defect"),
    TASK("Task"),
    SUBTASK("Sub-task"),
    EPIC("Epic"),
    STORY("Story");

    private final String keyName;

    TicketType(String keyName) {
        this.keyName = keyName;
    }

    @JsonCreator
    public static TicketType fromString(String value) {
        return Arrays.stream(TicketType.values())
                .filter(type -> type.keyName.equalsIgnoreCase(value))
                .findAny().orElseThrow(IllegalArgumentException::new);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.getKeyName();
    }

}
