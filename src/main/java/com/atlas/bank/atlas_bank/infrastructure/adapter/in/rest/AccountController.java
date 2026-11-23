package com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import com.atlas.bank.atlas_bank.application.service.IAccountService;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountResponse;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.CreateAccountRequest;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.IAccountMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  private final IAccountService accountService;
  private final IAccountMapper accountMapper;
  private final AccountDashboardFacade dashboardFacade;

  @GetMapping("/{id}/dashboard")
  public ResponseEntity<DashboardResponse> getDashboard(@PathVariable Long id) {
    return ResponseEntity.ok(dashboardFacade.getDashboard(id));
  }

  @PostMapping
  public ResponseEntity<AccountResponse> create(
    @Valid @RequestBody CreateAccountRequest request
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(
      accountMapper.toResponse(accountService.create(accountMapper.toEntity(request)))
    );
  }

  @GetMapping
  public ResponseEntity<List<AccountResponse>> findAll() {
    List<AccountResponse> responses = accountService
      .findAll()
      .stream()
      .map(accountMapper::toResponse)
      .toList();
    return ResponseEntity.ok(responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(accountMapper.toResponse(accountService.findById(id)));
  }
}
