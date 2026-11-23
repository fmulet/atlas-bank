package com.atlas.bank.atlas_bank.domain.exception;

import java.math.BigDecimal;

/**
 * InsufficientFundsException
 */
public class InsufficientFundsException extends RuntimeException {

  public InsufficientFundsException(
    Long accountId,
    BigDecimal balance,
    BigDecimal amount
  ) {
    super(
      "Saldo insuficiente para realizar la transacción. Cuenta: " +
        accountId +
        ". Balance: " +
        balance +
        ", Monto: " +
        amount
    );
  }
}
