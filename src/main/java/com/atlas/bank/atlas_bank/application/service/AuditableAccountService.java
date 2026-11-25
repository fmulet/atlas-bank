package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import com.atlas.bank.atlas_bank.application.port.in.ICreateAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IGetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IListAccountsUseCase;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * AuditableAccountService
 */
@Slf4j
@Component
@Primary
public class AuditableAccountService
  implements ICreateAccountUseCase, IListAccountsUseCase, IGetAccountUseCase
{

  private final ICreateAccountUseCase createAccountUseCase;
  private final IGetAccountUseCase getAccountUseCase;
  private final IListAccountsUseCase listAccountsUseCase;

  public AuditableAccountService(
    @Qualifier("accountServiceImpl") ICreateAccountUseCase createAccountUseCase,
    @Qualifier("accountServiceImpl") IGetAccountUseCase getAccountUseCase,
    @Qualifier("accountServiceImpl") IListAccountsUseCase listAccountsUseCase
  ) {
    this.createAccountUseCase = createAccountUseCase;
    this.getAccountUseCase = getAccountUseCase;
    this.listAccountsUseCase = listAccountsUseCase;
  }

  @PostConstruct
  public void init() {
    log.info(
      "Clase real del accountService: {}",
      this.createAccountUseCase.getClass().getName()
    );
    log.info(
      "Clase real del accountService: {}",
      this.getAccountUseCase.getClass().getName()
    );
    log.info(
      "Clase real del accountService: {}",
      this.listAccountsUseCase.getClass().getName()
    );
  }

  @Override
  public Account create(CreateAccountCommand command) {
    log.info(
      "Creando cuenta - número: {}, titular: {}",
      command.accountNumber(),
      command.ownerName()
    );
    Account created = createAccountUseCase.create(command);
    log.info("Cuenta creada exitosamente - ID: {}", created.getId());
    return created;
  }

  @Override
  public List<Account> findAll() {
    return listAccountsUseCase.findAll();
  }

  @Override
  public Account findById(Long id) {
    return getAccountUseCase.findById(id);
  }
}
