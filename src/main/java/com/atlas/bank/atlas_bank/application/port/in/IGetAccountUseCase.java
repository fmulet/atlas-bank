package com.atlas.bank.atlas_bank.application.port.in;

import com.atlas.bank.atlas_bank.domain.model.account.Account;

/**
 * GetAccountUseCase
 */
public interface IGetAccountUseCase {
  Account findById(Long id);
}
