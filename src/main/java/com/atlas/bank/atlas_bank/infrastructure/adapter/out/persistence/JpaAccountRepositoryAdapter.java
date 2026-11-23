package com.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.atlas_bank.application.port.out.IAccountRepositoryPort;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * JpaAccountRepositoryAdapter
 */
@Repository
@RequiredArgsConstructor
public class JpaAccountRepositoryAdapter implements IAccountRepositoryPort {

  private final ISpringDataAccountRepository springDataAccountRepository;
  private final AccountPersistenceMapper accountPersistenceMapper;

  @Override
  public Optional<Account> findById(Long id) {
    return springDataAccountRepository
      .findById(id)
      .map(accountPersistenceMapper::toDomain);
  }

  @Override
  public List<Account> findAll() {
    return springDataAccountRepository
      .findAll()
      .stream()
      .map(accountPersistenceMapper::toDomain)
      .toList();
  }

  @Override
  public Account save(Account account) {
    account.initDefaults();

    AccountJpaEntity entity = accountPersistenceMapper.toJpaEntity(account);
    AccountJpaEntity saved = springDataAccountRepository.save(entity);
    return accountPersistenceMapper.toDomain(saved);
  }
}
