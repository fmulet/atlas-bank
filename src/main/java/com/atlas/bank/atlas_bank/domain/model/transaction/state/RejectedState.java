package com.atlas.bank.atlas_bank.domain.model.transaction.state;

import com.atlas.bank.atlas_bank.domain.model.transaction.TransactionStatus;

/**
 * RejectedState
 */
public record RejectedState() implements ITransactionState {
  @Override
  public TransactionStatus status() {
    return TransactionStatus.REJECTED;
  }
}
