package com.kbe5.domain.event.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GpsCondition {

    NORMAL("A","정상"),
    ABNORMAL("V","비정상"),
    NOT_INSTALLED("0","미장착");

    private final String status;
    private final String description;

    GpsCondition(String status, String description) {
        this.status = status;
        this.description = description;
    }

    @JsonCreator
    public static GpsCondition fromValue(String value) {

        return Arrays.stream(GpsCondition.values()).filter(i -> i.status
            .equals(value)).findAny().orElse(GpsCondition.ABNORMAL);
    }
}
