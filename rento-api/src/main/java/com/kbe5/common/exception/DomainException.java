package com.kbe5.common.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DomainException extends RuntimeException {

    private final String message;
    private final HttpStatus status;

    public DomainException(ErrorType errorType) {
        this.message = errorType.getMessage();
        this.status = errorType.getHttpStatus();
    }

    public ExceptionResponse toResponse() {
        return new ExceptionResponse(message);
    }

}
