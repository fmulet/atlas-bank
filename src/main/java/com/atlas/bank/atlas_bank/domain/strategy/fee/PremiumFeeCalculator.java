package com.atlas.bank.atlas_bank.domain.strategy.fee;

import com.atlas.bank.atlas_bank.domain.model.account.AccountType;
import java.math.BigDecimal;

/**
 * PremiumFeeCalculator
 */

public class PremiumFeeCalculator implements IFeeCalculator {

  @Override
  public boolean supports(AccountType accountType) {
    return AccountType.PREMIUM == accountType;
  }

  @Override
  public BigDecimal calculateFee(BigDecimal amount) {
    return BigDecimal.ZERO;
  }
}
