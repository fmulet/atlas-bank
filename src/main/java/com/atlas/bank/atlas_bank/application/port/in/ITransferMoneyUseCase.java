package com.atlas.bank.atlas_bank.application.port.in;

import com.atlas.bank.atlas_bank.application.command.TransferMoneyCommand;
import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;

/**
 * TranferMoneyUseCase
 */
public interface ITransferMoneyUseCase {
  Transaction execute(TransferMoneyCommand command);
}
