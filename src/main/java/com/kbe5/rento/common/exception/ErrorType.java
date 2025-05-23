package com.kbe5.rento.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorType {
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "부서를 찾을 수 없습니다"),

    // VALIDATION
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "값을 잘못 입력했습니다.");

    private final HttpStatus status;
    private final String message;
}
