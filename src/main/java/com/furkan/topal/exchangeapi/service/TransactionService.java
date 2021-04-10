package com.furkan.topal.exchangeapi.service;

import com.furkan.topal.exchangeapi.entity.Conversion;
import java.util.List;

public interface TransactionService {

  String getTransaction(Long transactionId, String transactionDate, int pageNo);

  String returnConversion(Conversion conversion);

  String returnConversionList(List<Conversion> conversionList);

  String getTransactionDetailWithIdAndDate(Long transactionId, String transactionDate);

  String getTransactionDetailWithDate(String transactionDate, int pageNo);

  String getTransactionDetailWithId(Long transactionId);
}
