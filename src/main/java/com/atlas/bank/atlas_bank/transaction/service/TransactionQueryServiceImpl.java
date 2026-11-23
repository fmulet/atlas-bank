package com.atlas.bank.atlas_bank.transaction.service;

import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import com.atlas.bank.atlas_bank.transaction.repository.ITransactionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TransactionQueryService
 */
@Service
@RequiredArgsConstructor
public class TransactionQueryServiceImpl implements ITransactionQueryService {

  private final ITransactionRepository transactionRepository;

  @Override
  public List<Transaction> getByAccountId(Long accountId) {
    return transactionRepository.findBySourceAccountIdOrTargetAccountId(
      accountId,
      accountId
    );
  }
}
