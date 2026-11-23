package com.atlas.bank.atlas_bank.domain.validation.chain;

import com.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import com.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * SufficientFundsValidator
 */
@Component
@Order(2)
public class SufficientFundsValidator implements ITransferValidator {

  @Override
  public void validate(TransferContext context) {
    if (context.from().getBalance().getAmount().compareTo(context.amount()) < 0) {
      throw new InsufficientFundsException(
        context.from().getId(),
        context.from().getBalance().getAmount(),
        context.amount()
      );
    }
  }
}
