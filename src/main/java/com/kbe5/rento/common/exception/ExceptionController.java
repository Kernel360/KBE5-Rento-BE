package com.kbe5.rento.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionResponse> customExceptionHandler(DomainException e) {
        log.warn("DomainException code: {}, message: {}", e.getResultCode(), e.getMessage());
        //항상 200 ok로 응답
        return ResponseEntity.ok(e.toResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception e) {
        log.error("[UnhandledException] {}", e.getMessage());
        return ResponseEntity.ok(
                new ExceptionResponse(
                        ErrorType.UNDEFINED_ERROR.getCode(),
                        ErrorType.UNDEFINED_ERROR.getMessage()
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.warn("ValidationException: {}", e.getMessage());

        return ResponseEntity.ok(
                new ExceptionResponse(
                        ErrorType.REQUIRED_PARAMETER_ERROR.getCode(), ErrorType.REQUIRED_PARAMETER_ERROR.getMessage()
                )
        );
    }
}
