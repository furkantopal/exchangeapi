package com.furkan.topal.exchangeapi.types;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {
  JSON_ERROR("EXCHANGEAPI101", "Encountered with problem while putting values to jsonObject.");

  private static final Map<String, ExceptionType> lookupMap = new HashMap<>();

  static {
    for (ExceptionType resultCodeType : ExceptionType.values()) {
      lookupMap.put(resultCodeType.code, resultCodeType);
    }
  }

  private final String code;
  private final String message;
}
