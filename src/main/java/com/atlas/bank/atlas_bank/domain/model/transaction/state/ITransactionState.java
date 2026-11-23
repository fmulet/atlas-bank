package com.atlas.bank.atlas_bank.domain.model.transaction.state;

import com.atlas.bank.atlas_bank.domain.model.transaction.TransactionStatus;

/**
 * ITransactionState
 */
public sealed interface ITransactionState
  permits PendingState, ValidatedState, ExecutedState, RejectedState, ReversedState
{
  TransactionStatus status();

  default ITransactionState execute() {
    throw new IllegalStateException(
      "No se puede ejecutar una transacción en estado " + status()
    );
  }

  default ITransactionState reverse() {
    throw new IllegalStateException(
      "No se puede revertir una transacción en estado " + status()
    );
  }

  default ITransactionState validate() {
    throw new IllegalStateException(
      "No se puede validar una transacción en estado " + status()
    );
  }

  default ITransactionState reject(String reason) {
    throw new IllegalStateException(
      "No se puede rechazar una transacción en estado " + reason + status()
    );
  }
}
