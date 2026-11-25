package com.atlas.bank.atlas_bank.application.facade;

import com.atlas.bank.atlas_bank.application.port.in.IGetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IGetTransactionsByAccountUseCase;
import com.atlas.bank.atlas_bank.application.query.DashboardReadModel;
import com.atlas.bank.atlas_bank.application.query.GetAccountStatementQuery;
import com.atlas.bank.atlas_bank.application.query.TransactionReadModel;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AccountDashboardFacade
 */
@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {

  private final IGetAccountUseCase accountService;
  private final IGetTransactionsByAccountUseCase transactionQueryService;

  public DashboardReadModel getDashboard(Long accountId) {
    Account account = accountService.findById(accountId);
    List<TransactionReadModel> transactionResponses =
      transactionQueryService.getByAccountId(new GetAccountStatementQuery(accountId));

    return DashboardReadModel.builder()
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
