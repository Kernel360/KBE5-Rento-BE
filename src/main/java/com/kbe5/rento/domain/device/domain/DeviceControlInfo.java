package com.kbe5.rento.domain.device.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeviceControlInfo {

    private long controlId;
    private String controlCode;
    private int controlValue;
}
