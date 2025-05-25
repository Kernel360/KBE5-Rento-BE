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
}
