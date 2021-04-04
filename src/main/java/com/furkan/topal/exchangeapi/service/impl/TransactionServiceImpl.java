package com.furkan.topal.exchangeapi.service.impl;

import static com.furkan.topal.exchangeapi.types.ExceptionType.JSON_OBJECT_ERROR;
import static com.furkan.topal.exchangeapi.utils.Constants.EXCHANGE_RATE;
import static com.furkan.topal.exchangeapi.utils.Constants.PAGING_SIZE;
import static com.furkan.topal.exchangeapi.utils.Constants.SOURCE_AMOUNT;
import static com.furkan.topal.exchangeapi.utils.Constants.SOURCE_CURRENCY;
import static com.furkan.topal.exchangeapi.utils.Constants.TARGET_AMOUNT;
import static com.furkan.topal.exchangeapi.utils.Constants.TARGET_CURRENCY;
import static com.furkan.topal.exchangeapi.utils.Constants.TRANSACTION_DATE;
import static com.furkan.topal.exchangeapi.utils.Constants.TRANSACTION_ID;

import com.furkan.topal.exchangeapi.entity.Conversion;
import com.furkan.topal.exchangeapi.exception.ExchangeApiException;
import com.furkan.topal.exchangeapi.repository.ConversionRepository;
import com.furkan.topal.exchangeapi.service.TransactionService;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

  public final ConversionRepository conversionRepository;

  public TransactionServiceImpl(ConversionRepository conversionRepository) {
    this.conversionRepository = conversionRepository;
  }

  @Override
  public String getTransaction(Long transactionId, String transactionDate, int pageNo) {

    final boolean IsNotTransactionDateNullOrEmpty =
        Objects.nonNull(transactionDate) && !transactionDate.isEmpty();

    if (Objects.nonNull(transactionId) && IsNotTransactionDateNullOrEmpty) {

      return getTransactionDetailWithIdAndDate(transactionId, transactionDate);

    } else if (Objects.nonNull(transactionId)) {

      return getTransactionDetailWithId(transactionId);

    } else if (IsNotTransactionDateNullOrEmpty) {

      return getTransactionDetailWithDate(transactionDate, pageNo);
    }
    return "Please at least give one not null parameter as transactionId or transactionDate.";
  }

  public String returnConversion(Conversion conversion) {

    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put(TRANSACTION_ID, conversion.getTransactionId());
      jsonObject.put(TRANSACTION_DATE, conversion.getTransactionDate());
      jsonObject.put(SOURCE_CURRENCY, conversion.getSourceCurrency());
      jsonObject.put(TARGET_CURRENCY, conversion.getTargetCurrency());
      jsonObject.put(SOURCE_AMOUNT, conversion.getSourceAmount());
      jsonObject.put(EXCHANGE_RATE, conversion.getExchangeRate());
      jsonObject.put(TARGET_AMOUNT, conversion.getTargetAmount());
    } catch (JSONException e) {
      log.error(
          "Failed to put conversion values to jsonObject while returning the transaction details.",
          e);
      throw new ExchangeApiException(
          JSON_OBJECT_ERROR, "Returning conversion as jsonObject failed.", e);
    }

    return jsonObject.toString();
  }

  public String returnConversionList(List<Conversion> conversionList) {

    StringBuilder conversionListString = new StringBuilder();

    conversionList.forEach(conversion -> conversionListString.append(returnConversion(conversion)));

    if (!conversionListString.toString().isEmpty()) {
      return conversionListString.toString();
    } else {
      log.info("There is no conversion record for this transactionDate.");
      return "There is no conversion record for this transactionDate.";
    }
  }

  public String getTransactionDetailWithIdAndDate(Long transactionId, String transactionDate) {

    Conversion conversion =
        conversionRepository.findByTransactionIdAndTransactionDate(transactionId, transactionDate);

    if (Objects.nonNull(conversion)) {
      log.info("Desired conversion info: {}.", conversion.toString());
      return returnConversion(conversion);

    } else {
      log.info("There is no conversion record for this transactionId and transactionDate.");
      return "There is no conversion record for this transactionId and transactionDate.";
    }
  }

  public String getTransactionDetailWithDate(String transactionDate, int pageNo) {

    List<Conversion> conversionList =
        conversionRepository.findByTransactionDate(
            transactionDate, PageRequest.of(pageNo, PAGING_SIZE));

    if (Objects.nonNull(conversionList)) {

      log.info("Desired conversions info list: {}.", conversionList.toString());

      return returnConversionList(conversionList);
    } else {
      log.info("There is no conversion record for this transactionDate.");
      return "There is no conversion record for this transactionDate.";
    }
  }

  public String getTransactionDetailWithId(Long transactionId) {

    Conversion conversion = conversionRepository.findByTransactionId(transactionId);

    if (Objects.nonNull(conversion)) {
      log.info("Desired conversion info: {}.", conversion.toString());
      return returnConversion(conversion);

    } else {
      log.info("There is no conversion record for this transactionId.");
      return "There is no conversion record for this transactionId.";
    }
  }
}
