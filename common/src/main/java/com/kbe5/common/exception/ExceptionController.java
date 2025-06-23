package com.kbe5.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionResponse> customExceptionHandler(DomainException e) {
        log.warn("DomainException code: {}, message: {}", e.getStatus(), e.getMessage());
        return ResponseEntity.status(e.getStatus())
            .body(e.toResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception e) {
        log.error("[UnhandledException] {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.warn("ValidationException: {}", e.getMessage());

        return ResponseEntity.badRequest().body(new ExceptionResponse(ErrorType.VALIDATION_ERROR.getMessage()));

    }

    @ExceptionHandler(DeviceException.class)
    public ResponseEntity<DeviceExceptionResponse> deviceExceptionHandler(DeviceException e) {
        log.warn("DeviceException code: {}, message: {}", e.getResultCode(), e.getResultMessage());
        return ResponseEntity.ok(new DeviceExceptionResponse(e.getResultCode(), e.getResultMessage()));
    }
}
