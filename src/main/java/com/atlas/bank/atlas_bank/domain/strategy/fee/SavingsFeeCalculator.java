package com.atlas.bank.atlas_bank.domain.strategy.fee;

import com.atlas.bank.atlas_bank.domain.model.account.AccountType;
import java.math.BigDecimal;

/**
 * SavingsFeeCalculator
 */

public class SavingsFeeCalculator implements IFeeCalculator {

  @Override
  public boolean supports(AccountType accountType) {
    return AccountType.SAVINGS == accountType;
  }

  @Override
  public BigDecimal calculateFee(BigDecimal amount) {
    return amount.multiply(new BigDecimal("0.01"));
  }
}
