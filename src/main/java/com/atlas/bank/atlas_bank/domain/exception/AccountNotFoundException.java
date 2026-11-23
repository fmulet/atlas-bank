package com.atlas.bank.atlas_bank.domain.exception;

/**
 * AccountNotFoundException
 */
public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(Long id) {
    super("No se encontró la cuenta con el id: " + id + ".");
  }
}
