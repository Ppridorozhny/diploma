package com.diploma.backend.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Priority {

    LOW("Low"),
    NORMAL("Normal"),
    MAJOR("Major"),
    CRITICAL("Critical"),
    BLOCKER("Blocker");

    private final String keyName;

    Priority(String keyName) {
        this.keyName = keyName;
    }

    @JsonCreator
    public static Priority fromString(String value) {
        return Arrays.stream(Priority.values())
                .filter(type -> type.keyName.equalsIgnoreCase(value))
                .findAny().orElseThrow(IllegalArgumentException::new);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.getKeyName();
    }

}
