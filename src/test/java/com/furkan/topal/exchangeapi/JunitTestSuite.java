package com.furkan.topal.exchangeapi;

import com.furkan.topal.exchangeapi.service.ConversionServiceTest;
import com.furkan.topal.exchangeapi.service.TransactionServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ConversionServiceTest.class, TransactionServiceTest.class})
public class JunitTestSuite {}
