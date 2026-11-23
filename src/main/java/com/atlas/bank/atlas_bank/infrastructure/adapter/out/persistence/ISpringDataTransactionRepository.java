package com.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ISpringDataTransactionRepository
 */
public interface ISpringDataTransactionRepository
  extends JpaRepository<TransactionJpaEntity, Long>
{
  List<TransactionJpaEntity> findBySourceAccountIdOrTargetAccountId(
    Long sourceAccountId,
    Long targetAccountId
  );
}
