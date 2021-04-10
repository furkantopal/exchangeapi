package com.furkan.topal.exchangeapi.service.impl;

import static com.furkan.topal.exchangeapi.types.ExceptionType.JSON_ERROR;
import static com.furkan.topal.exchangeapi.utils.Constants.PAGING_SIZE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.furkan.topal.exchangeapi.entity.Conversion;
import com.furkan.topal.exchangeapi.exception.ExchangeApiException;
import com.furkan.topal.exchangeapi.repository.ConversionRepository;
import com.furkan.topal.exchangeapi.service.TransactionService;
import java.util.List;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

    final boolean IsTransactionDateNullOrEmpty =
        !Objects.nonNull(transactionDate) || transactionDate.isEmpty();

    if (Objects.nonNull(transactionId) && !IsTransactionDateNullOrEmpty) {

      return getTransactionDetailWithIdAndDate(transactionId, transactionDate);

    } else if (Objects.nonNull(transactionId)) {

      return getTransactionDetailWithId(transactionId);

    } else if (!IsTransactionDateNullOrEmpty) {

      return getTransactionDetailWithDate(transactionDate, pageNo);
    }
    return "Please at least give one not null parameter as transactionId or transactionDate.";
  }

  public String returnConversion(Conversion conversion) {

    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      String jsonString = objectWriter.writeValueAsString(conversion);
      log.info("Returning desired conversion info as: {}.", jsonString);
      return jsonString;
    } catch (JsonProcessingException e) {
      log.error(
          "Failed to convert conversion values to Json string while returning the transaction details.",
          e);
      throw new ExchangeApiException(JSON_ERROR, "Returning conversion as Json string failed.", e);
    }
  }

  @SneakyThrows
  public String returnConversionList(List<Conversion> conversionList) {

    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      String jsonString = objectWriter.writeValueAsString(conversionList);
      log.info("Returning desired conversions info as: {}.", jsonString);
      return jsonString;
    } catch (JsonProcessingException e) {
      log.error(
          "Failed to convert conversion list values to Json string while returning the transaction list details.",
          e);
      throw new ExchangeApiException(JSON_ERROR, "Returning conversion as Json string failed.", e);
    }
  }

  public String getTransactionDetailWithIdAndDate(Long transactionId, String transactionDate) {

    Conversion conversion =
        conversionRepository.findByTransactionIdAndTransactionDate(transactionId, transactionDate);

    if (Objects.nonNull(conversion)) {
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

    if (Objects.nonNull(conversionList) && !conversionList.isEmpty()) {
      return returnConversionList(conversionList);
    } else {
      log.info("There is no conversion record for this transactionDate.");
      return "There is no conversion record for this transactionDate.";
    }
  }

  public String getTransactionDetailWithId(Long transactionId) {

    Conversion conversion = conversionRepository.findByTransactionId(transactionId);

    if (Objects.nonNull(conversion)) {
      return returnConversion(conversion);
    } else {
      log.info("There is no conversion record for this transactionId.");
      return "There is no conversion record for this transactionId.";
    }
  }
}
