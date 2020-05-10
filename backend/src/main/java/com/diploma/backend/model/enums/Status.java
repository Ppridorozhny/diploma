package com.diploma.backend.model.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Status {

    OPEN("Open"),
    IN_ASSESSMENT("In Assessment"),
    ASSESSED("Assessed"),
    IN_PROGRESS("In Progress"),
    IMPLEMENTED("Implemented"),
    READY_FOR_TESTING("Ready For Testing"),
    CLOSED("Closed");

    public static Status[] AFTER_OPEN = {IN_ASSESSMENT, ASSESSED, IN_PROGRESS, IMPLEMENTED, READY_FOR_TESTING, CLOSED};
    public static Status[] AFTER_IN_ASSESSMENT = {OPEN, ASSESSED};
    public static Status[] AFTER_ASSESSED = {OPEN, IN_PROGRESS, READY_FOR_TESTING, CLOSED};
    public static Status[] AFTER_IN_PROGRESS = {IMPLEMENTED, READY_FOR_TESTING, CLOSED};
    public static Status[] AFTER_IMPLEMENTED = {READY_FOR_TESTING, CLOSED};
    public static Status[] AFTER_READY_FOR_TESTING = {OPEN, CLOSED};
    public static Status[] AFTER_CLOSED = {OPEN};

    private final String keyName;

    Status(String keyName) {
        this.keyName = keyName;
    }

    @JsonCreator
    public static Status fromString(String value) {
        return Arrays.stream(Status.values())
                .filter(type -> type.keyName.equalsIgnoreCase(value))
                .findAny().orElseThrow(IllegalArgumentException::new);
    }

    @JsonValue
    public String getName() {
        return this.getKeyName();
    }
}
