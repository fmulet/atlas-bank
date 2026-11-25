package com.atlas.bank.atlas_bank.application.query;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

/**
 * DashboardReadModel
 */
@Builder
public record DashboardReadModel(
  Long accountId,
  String accountNumber,
  String ownerName,
  String type,
  BigDecimal balance,
  String status,
  List<TransactionReadModel> recentTransactions
) {}
