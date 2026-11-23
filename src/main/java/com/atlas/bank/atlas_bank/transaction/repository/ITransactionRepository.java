package com.atlas.bank.atlas_bank.transaction.repository;

import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ITransactionRepository
 */
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId);
}
