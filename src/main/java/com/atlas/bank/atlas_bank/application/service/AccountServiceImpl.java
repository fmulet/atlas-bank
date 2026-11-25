package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import com.atlas.bank.atlas_bank.application.port.in.ICreateAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IGetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IListAccountsUseCase;
import com.atlas.bank.atlas_bank.application.port.out.IAccountRepositoryPort;
import com.atlas.bank.atlas_bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import com.atlas.bank.atlas_bank.domain.model.shared.Currency;
import com.atlas.bank.atlas_bank.domain.model.shared.Email;
import com.atlas.bank.atlas_bank.domain.model.shared.Money;
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
  public Account create(CreateAccountCommand command) {
    Account account = Account.builder()
      .accountNumber(command.accountNumber())
      .ownerName(command.ownerName())
      .email(Email.of(command.email()))
      .type(command.accountType())
      .balance(Money.of(command.balance(), Currency.CLP))
      .build();
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
