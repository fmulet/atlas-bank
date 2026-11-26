package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.application.command.CloseAccountCommand;
import com.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import com.atlas.bank.atlas_bank.application.port.in.CloseAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.ICreateAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IGetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IListAccountsUseCase;
import com.atlas.bank.atlas_bank.application.port.out.IAccountRepositoryPort;
import com.atlas.bank.atlas_bank.domain.event.AccountClosedEvent;
import com.atlas.bank.atlas_bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import com.atlas.bank.atlas_bank.domain.model.shared.Currency;
import com.atlas.bank.atlas_bank.domain.model.shared.Email;
import com.atlas.bank.atlas_bank.domain.model.shared.Money;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AccountService
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl
  implements ICreateAccountUseCase, IListAccountsUseCase, IGetAccountUseCase, CloseAccountUseCase
{

  private final IAccountRepositoryPort accountRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

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

  @Override
  @Transactional
  @CacheEvict(value = "accounts", key = "#command.accountId()")
  public Account close(CloseAccountCommand command) {
    Account account = accountRepository
      .findById(command.accountId())
      .orElseThrow(() -> new AccountNotFoundException(command.accountId()));

    account.close();
    Account closed = accountRepository.save(account);

    applicationEventPublisher.publishEvent(
      new AccountClosedEvent(
        closed.getId(),
        closed.getAccountNumber(),
        closed.getOwnerName(),
        LocalDateTime.now()
      )
    );

    return closed;
  }
}
