package com.atlas.bank.atlas_bank.domain.model.transaction.state;

import com.atlas.bank.atlas_bank.domain.model.transaction.TransactionStatus;

/**
 * ExecutedState
 */
public record ExecutedState() implements ITransactionState {
  @Override
  public TransactionStatus status() {
    return TransactionStatus.EXECUTED;
  }

  @Override
  public ITransactionState reverse() {
    return new ReversedState();
  }
}
