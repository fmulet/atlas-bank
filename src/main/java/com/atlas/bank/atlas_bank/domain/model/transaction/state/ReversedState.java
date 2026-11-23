package com.atlas.bank.atlas_bank.domain.model.transaction.state;

import com.atlas.bank.atlas_bank.domain.model.transaction.TransactionStatus;

/**
 * ReversedState
 */
public record ReversedState() implements ITransactionState {
  @Override
  public TransactionStatus status() {
    return TransactionStatus.REVERSED;
  }
}
