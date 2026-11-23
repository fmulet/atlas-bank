package com.atlas.bank.atlas_bank.application.service;

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
public class AuditableAccountService implements IAccountService {

  private IAccountService delegate;

  public AuditableAccountService(
    @Qualifier("accountServiceImpl") IAccountService delegate
  ) {
    this.delegate = delegate;
  }

  @PostConstruct
  public void init() {
    log.info("Clase real del accountService: {}", delegate.getClass().getName());
  }

  @Override
  public Account create(Account account) {
    log.info(
      "Creando cuenta - número: {}, titular: {}",
      account.getAccountNumber(),
      account.getOwnerName()
    );
    Account created = delegate.create(account);
    log.info("Cuenta creada exitosamente - ID: {}", created.getId());
    return created;
  }

  @Override
  public List<Account> findAll() {
    return delegate.findAll();
  }

  @Override
  public Account findById(Long id) {
    return delegate.findById(id);
  }
}
