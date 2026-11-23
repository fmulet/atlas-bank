package com.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.atlas_bank.application.port.out.ITransactionRepositoryPort;
import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

/**
 * JpaTransactionRepositoryAdapter
 */
@Repository
@RequiredArgsConstructor
public class JpaTransactionRepositoryAdapter implements ITransactionRepositoryPort {

  private final ISpringDataTransactionRepository springDataTransactionRepository;
  private final TransactionPersistenceMapper transactionPersistenceMapper;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public Transaction save(Transaction transaction) {
    transaction.initDefaults();
    TransactionJpaEntity entity = transactionPersistenceMapper.toJpaEntity(transaction);
    TransactionJpaEntity savedEntity = springDataTransactionRepository.save(entity);
    Transaction savedTransaction = transactionPersistenceMapper.toDomain(savedEntity);
    transaction.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
    transaction.clearDomainEvents();
    return savedTransaction;
  }

  @Override
  public List<Transaction> findBySourceAccountIdOrTargetAccountId(
    Long sourceAccountId,
    Long targetAccountId
  ) {
    return springDataTransactionRepository
      .findBySourceAccountIdOrTargetAccountId(sourceAccountId, targetAccountId)
      .stream()
      .map(transactionPersistenceMapper::toDomain)
      .toList();
  }
}
