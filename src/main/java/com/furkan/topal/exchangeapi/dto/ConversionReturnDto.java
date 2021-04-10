package com.furkan.topal.exchangeapi.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversionReturnDto {

  @NotNull
  @NotEmpty
  private BigDecimal targetAmount;
  @NotNull
  @NotEmpty
  private long transactionId;
}
