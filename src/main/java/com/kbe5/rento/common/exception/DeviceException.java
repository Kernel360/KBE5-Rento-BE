package com.kbe5.rento.common.exception;

import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import lombok.Getter;

@Getter
public class DeviceException extends RuntimeException{
    private final String resultCode;
    private final String resultMessage;
    public DeviceException(DeviceResultCode deviceResultCode) {
        this.resultCode = deviceResultCode.getCode();
        this.resultMessage = deviceResultCode.getMessage();
    }
}
