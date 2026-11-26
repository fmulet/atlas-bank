package com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import com.atlas.bank.atlas_bank.application.command.CloseAccountCommand;
import com.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import com.atlas.bank.atlas_bank.application.facade.AccountDashboardFacade;
import com.atlas.bank.atlas_bank.application.port.in.CloseAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.ICreateAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IGetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IListAccountsUseCase;
import com.atlas.bank.atlas_bank.application.query.DashboardReadModel;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountResponse;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.CreateAccountRequest;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.IAccountMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AccountController
 */
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

  private final ICreateAccountUseCase createAccountUseCase;
  private final IListAccountsUseCase listAccountsUseCase;
  private final IGetAccountUseCase getAccountUseCase;
  private final CloseAccountUseCase closeAccountUseCase;
  private final IAccountMapper accountMapper;
  private final AccountDashboardFacade dashboardFacade;

  @GetMapping("/{id}/dashboard")
  public ResponseEntity<DashboardReadModel> getDashboard(@PathVariable Long id) {
    return ResponseEntity.ok(dashboardFacade.getDashboard(id));
  }

  @PostMapping
  public ResponseEntity<AccountResponse> create(
    @Valid @RequestBody CreateAccountRequest request
  ) {
    CreateAccountCommand command = CreateAccountCommand.builder()
      .accountNumber(request.getAccountNumber())
      .ownerName(request.getOwnerName())
      .email(request.getEmail())
      .accountType(request.getType())
      .balance(request.getBalance())
      .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(
      accountMapper.toResponse(createAccountUseCase.create(command))
    );
  }

  @GetMapping
  public ResponseEntity<List<AccountResponse>> findAll() {
    List<AccountResponse> responses = listAccountsUseCase
      .findAll()
      .stream()
      .map(accountMapper::toResponse)
      .toList();
    return ResponseEntity.ok(responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(accountMapper.toResponse(getAccountUseCase.findById(id)));
  }

  @PatchMapping("/{id}/close")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<AccountResponse> close(@PathVariable Long id) {
    CloseAccountCommand command = CloseAccountCommand.builder().accountId(id).build();
    return ResponseEntity.ok(
      accountMapper.toResponse(closeAccountUseCase.close(command))
    );
  }
}
