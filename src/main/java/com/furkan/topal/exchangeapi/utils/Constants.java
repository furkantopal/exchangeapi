package com.furkan.topal.exchangeapi.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

  public static final String TRANSACTION_ID = "transaction_id";
  public static final String TRANSACTION_DATE = "transaction_date";
  public static final String SOURCE_CURRENCY = "source_currency";
  public static final String TARGET_CURRENCY = "target_currency";
  public static final String SOURCE_AMOUNT = "source_amount";
  public static final String EXCHANGE_RATE = "exchange_rate";
  public static final String TARGET_AMOUNT = "target_amount";
  public static final String RATES = "rates";
  public static final String HTTPS = "https";
  public static final String RATESAPI_HOST = "api.ratesapi.io/api";
  public static final String RATESAPI_PATH = "/latest";
  public static final String RATESAPI_PARAM_BASE = "base";
  public static final String RATESAPI_PARAM_SYMBOL = "symbols";
  public static final int PAGING_SIZE = 3;
}
