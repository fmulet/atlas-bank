package com.atlas.bank.atlas_bank.domain.strategy.fee;

import com.atlas.bank.atlas_bank.domain.model.account.AccountType;
import java.math.BigDecimal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * CheckingFeeCalculator
 */
@Component
@Order(1)
public class CheckingFeeCalculator implements IFeeCalculator {

  @Override
  public boolean supports(AccountType accountType) {
    return AccountType.CHECKING == accountType;
  }

  @Override
  public BigDecimal calculateFee(BigDecimal amount) {
    return amount.multiply(new BigDecimal("0.015"));
  }
}
