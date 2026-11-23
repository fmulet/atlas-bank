package com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import com.atlas.bank.atlas_bank.application.port.in.ITransferMoneyUseCase;
import com.atlas.bank.atlas_bank.application.service.ITransactionQueryService;
import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.ITransactionMapper;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransferRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TransactionController
 */
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final ITransferMoneyUseCase transferMoneyUseCase;
  private final ITransactionQueryService transactionQueryService;
  private final ITransactionMapper transactionMapper;

  @PostMapping("/transfer")
  public ResponseEntity<TransactionResponse> transfer(
    @Valid @RequestBody TransferRequest request
  ) {
    Transaction transaction = transferMoneyUseCase.execute(
      request.getFromAccountId(),
      request.getToAccountId(),
      request.getAmount()
    );
    return ResponseEntity.ok(transactionMapper.toResponse(transaction));
  }

  @GetMapping("/{id}/transactions")
  public ResponseEntity<List<TransactionResponse>> getTransactions(
    @PathVariable Long id
  ) {
    List<TransactionResponse> transactions = transactionQueryService
      .getByAccountId(id)
      .stream()
      .map(transactionMapper::toResponse)
      .toList();
    return ResponseEntity.ok(transactions);
  }
}
