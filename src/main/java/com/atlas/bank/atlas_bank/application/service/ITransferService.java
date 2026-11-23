package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import java.math.BigDecimal;

/**
 * ITransferService
 */
public interface ITransferService {
  Transaction execute(Long fromAccountId, Long toAccountId, BigDecimal amount);
}
