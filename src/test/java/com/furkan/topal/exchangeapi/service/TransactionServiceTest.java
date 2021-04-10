package com.furkan.topal.exchangeapi.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

  @Autowired
  public TransactionService transactionService;

  @Test
  public void getTransaction() {

    assertNotNull(transactionService.getTransactionDetailWithIdAndDate(1L, "2021-04-10"));

    assertNotNull(transactionService.getTransactionDetailWithId(1L));

    assertNotNull(transactionService.getTransactionDetailWithDate("2021-04-10", 0));
  }
}
