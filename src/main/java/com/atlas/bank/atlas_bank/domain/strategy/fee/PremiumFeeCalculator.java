package com.atlas.bank.atlas_bank.domain.strategy.fee;

import com.atlas.bank.atlas_bank.domain.model.account.AccountType;
import java.math.BigDecimal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * PremiumFeeCalculator
 */
@Component
@Order(1)
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
