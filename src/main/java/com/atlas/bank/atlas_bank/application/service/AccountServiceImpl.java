package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.application.port.in.ICreateAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IGetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IListAccountsUseCase;
import com.atlas.bank.atlas_bank.application.port.out.IAccountRepositoryPort;
import com.atlas.bank.atlas_bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AccountService
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl
  implements ICreateAccountUseCase, IListAccountsUseCase, IGetAccountUseCase
{

  private final IAccountRepositoryPort accountRepository;

  @Override
  @Transactional
  public Account create(Account account) {
    return accountRepository.save(account);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Account> findAll() {
    return accountRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "accounts", key = "#id")
  public Account findById(Long id) {
    return accountRepository
      .findById(id)
      .orElseThrow(() -> new AccountNotFoundException(id));
  }
}
