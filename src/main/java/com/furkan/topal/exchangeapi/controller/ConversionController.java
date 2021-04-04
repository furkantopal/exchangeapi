package com.furkan.topal.exchangeapi.controller;

import com.furkan.topal.exchangeapi.service.ConversionService;
import com.furkan.topal.exchangeapi.service.TransactionService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConversionController {

  @Autowired public ConversionService conversionService;

  @Autowired public TransactionService transactionService;

  @GetMapping("/exchangeRate")
  @ResponseBody
  public BigDecimal exchange(
      @RequestParam(value = "sourceCurrency") String sourceCurrency,
      @RequestParam(value = "targetCurrency") String targetCurrency) {

    return conversionService.getExchangeRate(sourceCurrency, targetCurrency);
  }

  @GetMapping("/conversion")
  @ResponseBody
  public String conversion(
      @RequestParam(value = "sourceAmount") BigDecimal sourceAmount,
      @RequestParam(value = "sourceCurrency") String sourceCurrency,
      @RequestParam(value = "targetCurrency") String targetCurrency) {

    return conversionService.convertCurrency(sourceAmount, sourceCurrency, targetCurrency);
  }

  @GetMapping("/conversion/list")
  @ResponseBody
  public String conversionList(
      @RequestParam(value = "transactionId", required = false) Long transactionId,
      @RequestParam(value = "transactionDate", required = false) String transactionDate,
      @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo) {

    return transactionService.getTransaction(transactionId, transactionDate, pageNo);
  }
}
