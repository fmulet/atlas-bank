package com.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ISpringDataCustomerRepository
 */
public interface ISpringDataCustomerRepository
  extends JpaRepository<CustomerJpaEntity, Long> {}
