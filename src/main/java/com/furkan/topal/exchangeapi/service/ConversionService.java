package com.furkan.topal.exchangeapi.service;

import java.math.BigDecimal;

public interface ConversionService {

  String convertCurrency(BigDecimal amount, String sourceCurrency, String targetCurrency);

  BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency);
}
