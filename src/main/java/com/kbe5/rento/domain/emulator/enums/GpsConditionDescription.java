package com.kbe5.rento.domain.emulator.enums;

import lombok.Getter;

@Getter
public enum GpsConditionDescription {
    NORMAL('A',"정상"),
    ABNORMAL('V',"비정상"),
    NOT_INSTALLED('P',"미장착");

    private final Character status;
    private final String description;

    GpsConditionDescription(Character status, String description) {
        this.status = status;
        this.description = description;
    }

}
