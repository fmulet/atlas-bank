package com.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ISpringDataAccountRepository
 */
public interface ISpringDataAccountRepository
  extends JpaRepository<AccountJpaEntity, Long> {}
