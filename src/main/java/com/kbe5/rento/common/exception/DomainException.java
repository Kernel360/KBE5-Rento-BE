package com.kbe5.rento.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DomainException extends RuntimeException {

  private final String resultCode;
  private final String message;

  public DomainException(ErrorType errorType) {
    this.resultCode = errorType.getCode();
    this.message = errorType.getMessage();
  }

  public ExceptionResponse toResponse() {
    return new ExceptionResponse(resultCode, message);
  }
}
