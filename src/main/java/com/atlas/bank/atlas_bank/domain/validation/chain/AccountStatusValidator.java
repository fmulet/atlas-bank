package com.atlas.bank.atlas_bank.domain.validation.chain;

import com.atlas.bank.atlas_bank.domain.exception.AccountNotActiveException;
import com.atlas.bank.atlas_bank.domain.model.account.AccountStatus;
import com.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;

/**
 * AccountStatusValidator
 */

public class AccountStatusValidator implements ITransferValidator {

  @Override
  public void validate(TransferContext context) {
    if (context.from().getStatus() != AccountStatus.ACTIVE) {
      throw new AccountNotActiveException(
        context.from().getId(),
        context.from().getStatus().name()
      );
    }

    if (context.to().getStatus() != AccountStatus.ACTIVE) {
      throw new AccountNotActiveException(
        context.to().getId(),
        context.to().getStatus().name()
      );
    }
  }
}
