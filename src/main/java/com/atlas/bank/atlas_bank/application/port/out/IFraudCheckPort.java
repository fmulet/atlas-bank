package com.atlas.bank.atlas_bank.application.port.out;

import com.atlas.bank.atlas_bank.domain.model.shared.FraudCheckResult;
import java.math.BigDecimal;

/**
 * IFraudCheckPort
 */
public interface IFraudCheckPort {
  FraudCheckResult check(Long accountId, BigDecimal amount);
}
