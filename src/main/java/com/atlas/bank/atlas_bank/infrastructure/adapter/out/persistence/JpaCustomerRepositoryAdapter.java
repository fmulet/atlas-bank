package com.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.atlas_bank.application.port.out.ICustomerRepositoryPort;
import com.atlas.bank.atlas_bank.domain.model.customer.Customer;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * JpaCustomerRepositoryAdapter
 */
@Repository
@RequiredArgsConstructor
public class JpaCustomerRepositoryAdapter implements ICustomerRepositoryPort {

  private final ISpringDataCustomerRepository customerRepository;
  private final CustomerPersistenceMapper customerPersistenceMapper;

  @Override
  public Optional<Customer> findById(Long id) {
    return customerRepository.findById(id).map(customerPersistenceMapper::toDomain);
  }

  @Override
  public Customer save(Customer customer) {
    customer.initDefaults();
    CustomerJpaEntity entity = customerPersistenceMapper.toEntity(customer);
    CustomerJpaEntity savedEntity = customerRepository.save(entity);
    return customerPersistenceMapper.toDomain(savedEntity);
  }
}
