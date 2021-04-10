package com.furkan.topal.exchangeapi.service;

import static com.furkan.topal.exchangeapi.utils.Constants.RATES;
import static org.junit.Assert.assertNotNull;

import com.furkan.topal.exchangeapi.entity.Conversion;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConversionServiceTest {

  @Autowired
  public ConversionService conversionService;

  @Test
  public void convertCurrency() {

    BigDecimal exchangeRate = BigDecimal.valueOf(1);

    Conversion conversion =
        Conversion.builder()
            .transactionDate(LocalDate.now().toString())
            .sourceCurrency("TRY")
            .targetCurrency("USD")
            .sourceAmount(BigDecimal.valueOf(10))
            .exchangeRate(exchangeRate)
            .targetAmount(BigDecimal.valueOf(10).multiply(exchangeRate))
            .build();

    assertNotNull(conversionService.returnExchange(conversion));
  }

  @Test
  public void getExchangeRate() throws JSONException {

    JSONObject response = conversionService.getJSONRespond("TRY", "USD");

    assertNotNull(new BigDecimal(response.getJSONObject(RATES).getString("USD")));
  }
}
