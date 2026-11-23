package com.atlas.bank.atlas_bank.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * CreateTransactionRequest
 */
@Data
public class TransactionResponse {

  private Long id;

  private String type; // DEPOSIT, WITHDRAWAL, TRANSFER

  private Long sourceAccountId;

  private Long targetAccountId;

  private BigDecimal amount;

  private BigDecimal fee;

  private String status;

  private LocalDateTime createdAt;
}
