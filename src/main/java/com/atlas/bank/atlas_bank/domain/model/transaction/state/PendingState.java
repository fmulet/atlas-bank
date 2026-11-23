package com.atlas.bank.atlas_bank.domain.model.transaction.state;

import com.atlas.bank.atlas_bank.domain.model.transaction.TransactionStatus;

/**
 * PendingState
 */
public record PendingState() implements ITransactionState {
  @Override
  public TransactionStatus status() {
    return TransactionStatus.PENDING;
  }

  @Override
  public ITransactionState validate() {
    return new ValidatedState();
  }

  @Override
  public ITransactionState reject(String reason) {
    return new RejectedState();
  }
}
