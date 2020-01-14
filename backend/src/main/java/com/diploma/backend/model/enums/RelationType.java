package com.diploma.backend.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RelationType {

    RELATES_TO("Relates to"),
    BLOCKS("Blocks"),
    BLOCKED_BY("Blocked by"),
    DUPLICATES("Duplicates"),
    CHILD("Child"),
    PARENT("Parent");

    private final String keyName;

    RelationType(String keyName) {
        this.keyName = keyName;
    }

    @JsonCreator
    public static RelationType fromString(String value) {
        return Arrays.stream(RelationType.values())
                .filter(type -> type.keyName.equalsIgnoreCase(value))
                .findAny().orElseThrow(IllegalArgumentException::new);
    }

    @JsonValue
    @Override
    public String toString() {
        return this.getKeyName();
    }

}
