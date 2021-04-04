package com.furkan.topal.exchangeapi.service;

public interface TransactionService {

  String getTransaction(Long transactionId, String transactionDate, int pageNo);
}
