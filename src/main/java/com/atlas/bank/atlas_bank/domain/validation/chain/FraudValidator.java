package com.atlas.bank.atlas_bank.domain.validation.chain;

import com.atlas.bank.atlas_bank.application.port.out.IFraudCheckPort;
import com.atlas.bank.atlas_bank.domain.exception.FraudCheckException;
import com.atlas.bank.atlas_bank.domain.model.shared.FraudCheckResult;
import com.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;
import lombok.RequiredArgsConstructor;

/**
 * FraudeValidator
 */

@RequiredArgsConstructor
public class FraudValidator implements ITransferValidator {

  private final IFraudCheckPort fraudChecker;

  @Override
  public void validate(TransferContext context) {
    FraudCheckResult fraudCheckResult = fraudChecker.check(
      context.from().getId(),
      context.amount()
    );
    if (fraudCheckResult.blocked()) {
      throw new FraudCheckException(fraudCheckResult.reason());
    }
  }
}
