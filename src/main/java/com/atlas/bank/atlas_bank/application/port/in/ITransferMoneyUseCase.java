package com.atlas.bank.atlas_bank.application.port.in;

import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import java.math.BigDecimal;

/**
 * TranferMoneyUseCase
 */
public interface ITransferMoneyUseCase {
  Transaction execute(Long fromAccountId, Long toAccountId, BigDecimal amount);
}
