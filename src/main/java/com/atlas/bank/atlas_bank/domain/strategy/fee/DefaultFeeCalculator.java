package com.atlas.bank.atlas_bank.domain.strategy.fee;

import com.atlas.bank.atlas_bank.domain.model.account.AccountType;
import java.math.BigDecimal;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * DefaultFeeCalculator
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultFeeCalculator implements IFeeCalculator {

  @Override
  public boolean supports(AccountType accountType) {
    return true;
  }

  @Override
  public BigDecimal calculateFee(BigDecimal amount) {
    return BigDecimal.ZERO;
  }
}
