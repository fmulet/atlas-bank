package com.atlas.bank.atlas_bank.domain.validation.chain;

import com.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;

/**
 * ITransferValidator
 */
public interface ITransferValidator {
  void validate(TransferContext ctx);
}
