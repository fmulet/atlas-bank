package com.atlas.bank.atlas_bank.domain.model.transaction.state;

import com.atlas.bank.atlas_bank.domain.model.transaction.TransactionStatus;

/**
 * ValidatedState
 */
public record ValidatedState() implements ITransactionState {
  @Override
  public TransactionStatus status() {
    return TransactionStatus.VALIDATED;
  }

  @Override
  public ITransactionState reject(String reason) {
    return new RejectedState();
  }

  @Override
  public ITransactionState execute() {
    return new ExecutedState();
  }
}
