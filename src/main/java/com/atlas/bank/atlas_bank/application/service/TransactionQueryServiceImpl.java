package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.application.port.out.ITransactionRepositoryPort;
import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TransactionQueryService
 */
@Service
@RequiredArgsConstructor
public class TransactionQueryServiceImpl implements ITransactionQueryService {

  private final ITransactionRepositoryPort transactionRepository;

  @Override
  public List<Transaction> getByAccountId(Long accountId) {
    return transactionRepository.findBySourceAccountIdOrTargetAccountId(
      accountId,
      accountId
    );
  }
}
