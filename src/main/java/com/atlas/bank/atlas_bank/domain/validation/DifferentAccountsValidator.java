package com.atlas.bank.atlas_bank.domain.validation;

import com.atlas.bank.atlas_bank.transaction.dto.TransferRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * DifferentAccountsValidator
 */
public class DifferentAccountsValidator
  implements ConstraintValidator<DifferentAccounts, TransferRequest>
{

  @Override
  public boolean isValid(TransferRequest value, ConstraintValidatorContext context) {
    if (value.getFromAccountId() == null || value.getToAccountId() == null) {
      return true;
    }

    return !value.getFromAccountId().equals(value.getToAccountId());
  }
}
