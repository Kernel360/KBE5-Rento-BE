package com.kbe5.common.exception;

import lombok.Getter;

@Getter
public class DeviceException extends RuntimeException{

    private final String resultCode;

    private final String resultMessage;

    public DeviceException(DeviceResultCode deviceResultCode) {
        this.resultCode = deviceResultCode.getCode();
        this.resultMessage = deviceResultCode.getMessage();
    }

    public DeviceExceptionResponse toResponse() {

        return new DeviceExceptionResponse(resultCode, resultMessage);
    }

}
