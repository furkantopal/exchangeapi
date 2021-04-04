package com.furkan.topal.exchangeapi.service.impl;

import static com.furkan.topal.exchangeapi.types.ExceptionType.JSON_OBJECT_ERROR;
import static com.furkan.topal.exchangeapi.utils.Constants.HTTPS;
import static com.furkan.topal.exchangeapi.utils.Constants.RATES;
import static com.furkan.topal.exchangeapi.utils.Constants.RATESAPI_HOST;
import static com.furkan.topal.exchangeapi.utils.Constants.RATESAPI_PARAM_BASE;
import static com.furkan.topal.exchangeapi.utils.Constants.RATESAPI_PARAM_SYMBOL;
import static com.furkan.topal.exchangeapi.utils.Constants.RATESAPI_PATH;
import static com.furkan.topal.exchangeapi.utils.Constants.TARGET_AMOUNT;
import static com.furkan.topal.exchangeapi.utils.Constants.TRANSACTION_ID;

import com.furkan.topal.exchangeapi.entity.Conversion;
import com.furkan.topal.exchangeapi.exception.ExchangeApiException;
import com.furkan.topal.exchangeapi.repository.ConversionRepository;
import com.furkan.topal.exchangeapi.service.ConversionService;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConversionServiceImpl implements ConversionService {

  public final ConversionRepository conversionRepository;

  public ConversionServiceImpl(ConversionRepository conversionRepository) {
    this.conversionRepository = conversionRepository;
  }

  @Override
  public String convertCurrency(
      BigDecimal sourceAmount, String sourceCurrency, String targetCurrency) {

    BigDecimal exchangeRate = getExchangeRate(sourceCurrency, targetCurrency);

    Conversion conversion =
        Conversion.builder()
            .transactionDate(LocalDate.now().toString())
            .sourceCurrency(sourceCurrency)
            .targetCurrency(targetCurrency)
            .sourceAmount(sourceAmount)
            .exchangeRate(exchangeRate)
            .targetAmount(sourceAmount.multiply(exchangeRate))
            .build();
    conversionRepository.save(conversion);
    log.info("Conversion saved to the repository as: {}.", conversion.toString());

    return returnExchange(conversion);
  }

  @SneakyThrows
  public JSONObject getJSONRespond(String sourceCurrency, String targetCurrency) {

    log.info(
        "Request taken as sourceCurrency: {} to targetCurrency: {}.",
        sourceCurrency,
        targetCurrency);

    HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    URIBuilder builder = new URIBuilder();
    builder
        .setScheme(HTTPS)
        .setHost(RATESAPI_HOST)
        .setPath(RATESAPI_PATH)
        .setParameter(RATESAPI_PARAM_BASE, sourceCurrency)
        .setParameter(RATESAPI_PARAM_SYMBOL, targetCurrency);
    URI uri = builder.build();

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(new URI(uri.toString()))
            .version(HttpClient.Version.HTTP_2)
            .GET()
            .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    String jsonResponse = response.body();

    log.info("RatesApi returned exchange request as: {}", jsonResponse);
    return new JSONObject(jsonResponse);
  }

  @SneakyThrows
  public BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency) {

    JSONObject response = getJSONRespond(sourceCurrency, targetCurrency);
    String exchangeRate = response.getJSONObject(RATES).getString(targetCurrency);
    log.info("Exchange rate is: {}", exchangeRate);

    return new BigDecimal(exchangeRate);
  }

  public String returnExchange(Conversion conversion) {

    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put(TARGET_AMOUNT, conversion.getTargetAmount());
      jsonObject.put(TRANSACTION_ID, conversion.getTransactionId());
    } catch (JSONException e) {
      log.error(
          "Failed to put conversion values to jsonObject while returning exchange amount in target currency.",
          e);
      throw new ExchangeApiException(
          JSON_OBJECT_ERROR, "Returning conversion as jsonObject failed.", e);
    }

    log.info("Returning json response as: {}.", jsonObject.toString());
    return jsonObject.toString();
  }
}
