package com.atlas.bank.atlas_bank.domain.exception;

/**
 * AccountNotActiveException
 */
public class AccountNotActiveException extends RuntimeException {

  public AccountNotActiveException(Long accountId, String status) {
    super("La cuenta " + accountId + " no está activa, estado actual: " + status);
  }
}
