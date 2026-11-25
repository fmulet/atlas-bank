package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.application.port.in.IGetTransactionsByAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.out.ITransactionRepositoryPort;
import com.atlas.bank.atlas_bank.application.query.GetAccountStatementQuery;
import com.atlas.bank.atlas_bank.application.query.TransactionReadModel;
import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TransactionQueryService
 */
@Service
@RequiredArgsConstructor
public class TransactionQueryServiceImpl implements IGetTransactionsByAccountUseCase {

  private final ITransactionRepositoryPort transactionRepository;

  @Override
  public List<TransactionReadModel> getByAccountId(GetAccountStatementQuery query) {
    return transactionRepository
      .findBySourceAccountIdOrTargetAccountId(query.accountId(), query.accountId())
      .stream()
      .map(this::toReadModel)
      .toList();
  }

  private TransactionReadModel toReadModel(Transaction transaction) {
    return new TransactionReadModel(
      transaction.getId(),
      transaction.getType().name(),
      transaction.getSourceAccountId(),
      transaction.getTargetAccountId(),
      transaction.getAmount(),
      transaction.getFee(),
      transaction.getStatus().name(),
      transaction.getCreatedAt()
    );
  }
}
