package com.kbe5.rento.common.apiresponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApiResultCode{
    SUCCESS(HttpStatus.OK, "SUCCESS", "요청이 성공했습니다."),
    DEVICE_NOT_FOUND(HttpStatus.NOT_FOUND, "DEVICE_404", "디바이스를 찾을 수 없습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED", "토큰이 만료되었습니다.");

    private final HttpStatus httpStatus;
    private final String resultCode;
    private final String message;

}
