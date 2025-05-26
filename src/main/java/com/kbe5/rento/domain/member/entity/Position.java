package com.kbe5.rento.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;

public enum Position {
    INTERN("인턴"),
    JUNIOR("사원"),
    SENIOR("주임"),
    ASSISTANT_MANAGER("대리"),
    MANAGER("과장"),
    GENERAL_MANAGER("부장"),
    DIRECTOR("이사"),
    VICE_PRESIDENT("부사장"),
    PRESIDENT("사장"),
    CEO("대표이사");

    private final String displayName;

    Position(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Position fromValue(String value) {
        for (Position position : Position.values()) {
            if (position.getDisplayName().equals(value)) {
                return position;
            }
        }
        throw new IllegalArgumentException(value + "올바르지 않은 직책입니다.");
    }

    public static List<String> getPositions(){
        return Arrays.stream(Position.values())
                .map(Position::getDisplayName)
                .toList();
    }

}
