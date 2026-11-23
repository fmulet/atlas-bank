package com.atlas.bank.atlas_bank.domain.strategy.fee;

import com.atlas.bank.atlas_bank.domain.model.account.AccountType;
import java.math.BigDecimal;

/**
 * FeeCalculatorService
 */
public interface IFeeCalculator {
  boolean supports(AccountType accountType);

  BigDecimal calculateFee(BigDecimal amount);
}
