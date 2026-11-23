package com.atlas.bank.atlas_bank.application.port.in;

import com.atlas.bank.atlas_bank.domain.model.account.Account;

/**
 * ICreateAccountUseCase
 */
public interface ICreateAccountUseCase {
  Account create(Account account);
}
