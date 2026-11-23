package com.atlas.bank.atlas_bank.application.port.out;

import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import java.util.List;

/**
 * ITransactionRepositoryPort
 */
public interface ITransactionRepositoryPort {
  Transaction save(Transaction transaction);

  List<Transaction> findBySourceAccountIdOrTargetAccountId(
    Long sourceAccountId,
    Long targetAccountId
  );
}
