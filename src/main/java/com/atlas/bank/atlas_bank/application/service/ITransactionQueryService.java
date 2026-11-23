package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import java.util.List;

/**
 * ITransactionQueryService
 */
public interface ITransactionQueryService {
  List<Transaction> getByAccountId(Long accountId);
}
