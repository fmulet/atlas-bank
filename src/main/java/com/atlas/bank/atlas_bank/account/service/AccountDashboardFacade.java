package com.atlas.bank.atlas_bank.account.service;

import com.atlas.bank.atlas_bank.application.service.IAccountService;
import com.atlas.bank.atlas_bank.application.service.ITransactionQueryService;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.ITransactionMapper;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AccountDashboardFacade
 */
@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {

  private final IAccountService accountService;
  private final ITransactionQueryService transactionQueryService;
  private final ITransactionMapper transactionMapper;

  public DashboardResponse getDashboard(Long accountId) {
    Account account = accountService.findById(accountId);
    List<TransactionResponse> transactionResponses = transactionQueryService
      .getByAccountId(accountId)
      .stream()
      .map(transactionMapper::toResponse)
      .toList();

    return DashboardResponse.builder()
      .accountId(account.getId())
      .accountNumber(account.getAccountNumber())
      .ownerName(account.getAccountNumber())
      .type(account.getType().name())
      .balance(account.getBalance().getAmount())
      .status(account.getStatus().name())
      .recentTransactions(transactionResponses)
      .build();
  }
}
