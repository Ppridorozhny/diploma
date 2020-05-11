package com.diploma.backend.model.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TicketType {

    DEFECT("Defect"),
    TASK("Task"),
    SUBTASK("Sub-task"),
    EPIC("Epic"),
    STORY("Story"),
    DEFAULT("Default");

    public static final TicketType[] FOR_EPIC = {STORY, TASK, DEFECT};
    public static final TicketType[] FOR_STORY = {TASK, DEFECT};
    public static final TicketType[] FOR_TASK = {SUBTASK};
    public static final TicketType[] FOR_DEFAULT = {STORY, TASK, DEFECT, EPIC};

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
    public String getName() {
        return this.getKeyName();
    }
}
