package com.furkan.topal.exchangeapi.exception;

import com.furkan.topal.exchangeapi.types.ExceptionType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ExchangeApiException extends RuntimeException {

  private final String code;
  private final String details;
  private final ExceptionType type;

  public ExchangeApiException(String code, String message) {
    super(message);
    this.code = code;
    this.details = null;
    this.type = null;
  }

  public ExchangeApiException(ExceptionType type) {
    super(type.getCode().concat(" - ").concat(type.getMessage()));
    this.code = type.getCode();
    this.type = type;
    this.details = type.getMessage();
  }

  public ExchangeApiException(ExceptionType type, String detail) {
    super(type.getCode().concat(" - ").concat(type.getMessage()).concat(" - ").concat(detail));
    this.code = type.getCode();
    this.details = detail;
    this.type = type;
  }

  public ExchangeApiException(ExceptionType type, Throwable cause) {
    super(type.getCode().concat(" - ").concat(type.getMessage()), cause);
    this.code = type.getCode();
    this.type = type;
    this.details = null;
  }

  public ExchangeApiException(ExceptionType type, String detail, Throwable cause) {
    super(
        type.getCode().concat(" - ").concat(type.getMessage()).concat(" - ").concat(detail), cause);
    this.code = type.getCode();
    this.details = detail;
    this.type = type;
  }
}
