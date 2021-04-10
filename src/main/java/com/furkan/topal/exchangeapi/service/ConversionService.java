package com.furkan.topal.exchangeapi.service;

import com.furkan.topal.exchangeapi.entity.Conversion;
import java.math.BigDecimal;
import org.json.JSONObject;

public interface ConversionService {

  String convertCurrency(BigDecimal amount, String sourceCurrency, String targetCurrency);

  BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency);

  JSONObject getJSONRespond(String sourceCurrency, String targetCurrency);

  String returnExchange(Conversion conversion);
}
