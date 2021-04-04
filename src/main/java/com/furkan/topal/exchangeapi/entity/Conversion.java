package com.furkan.topal.exchangeapi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "conversion")
@AllArgsConstructor
@NoArgsConstructor
public class Conversion implements Serializable {

  private static final long serialVersionUID = 1983877116107993546L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_id")
  private long transactionId;

  @Column(name = "transaction_date")
  private String transactionDate;

  @Column(name = "source_currency")
  private String sourceCurrency;

  @Column(name = "target_currency")
  private String targetCurrency;

  @Column(name = "source_amount")
  private BigDecimal sourceAmount;

  @Column(name = "exchange_rate")
  private BigDecimal exchangeRate;

  @Column(name = "target_amount")
  private BigDecimal targetAmount;
}
