package com.atlas.bank.atlas_bank.transaction.dto;

import com.atlas.bank.atlas_bank.domain.validation.DifferentAccounts;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

/**
 * TransferRequest
 */
@Data
@DifferentAccounts
public class TransferRequest {

  @NotNull(message = "La cuenta de origen no puede ser nula.")
  private Long fromAccountId;

  @NotNull(message = "La cuenta de destino no puede ser nula.")
  private Long toAccountId;

  @NotNull(message = "El monto no puede ser nulo.")
  @Positive(message = "El monto debe ser un valor positivo.")
  private BigDecimal amount;
}
