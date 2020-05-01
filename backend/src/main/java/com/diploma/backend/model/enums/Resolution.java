package com.diploma.backend.model.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Resolution {

    RESOLVER("Resolved"),
    UNRESOLVED("Unresolved"),
    NOT_A_BUG("Sub-task"),
    KNOWN_ISSUE("Known Issue"),
    DUPLICATE("Duplicate"),
    CANNOT_REPRODUCE("Cannot Reproduce"),
    CANCELED("Canceled");

    private final String keyName;

    Resolution(String keyName) {
        this.keyName = keyName;
    }

    @JsonCreator
    public static Resolution fromString(String value) {
        return Arrays.stream(Resolution.values())
                .filter(type -> type.keyName.equalsIgnoreCase(value))
                .findAny().orElseThrow(IllegalArgumentException::new);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.getKeyName();
    }
}
