package com.atlas.bank.atlas_bank.infrastructure.adapter.out.fraud;

import com.atlas.bank.atlas_bank.domain.model.shared.FraudCheckResult;
import java.math.BigDecimal;

/**
 * IFraudChecker
 */
public interface IFraudChecker {
  FraudCheckResult check(Long accountId, BigDecimal amount);
}
