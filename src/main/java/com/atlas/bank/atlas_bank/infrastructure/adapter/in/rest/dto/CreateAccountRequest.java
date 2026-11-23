package com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto;

import com.atlas.bank.atlas_bank.domain.model.account.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;

/**
 * CreateAccountRequest
 */
@Data
public class CreateAccountRequest {

  @NotBlank(message = "El número de cuenta no puede estar vacío.")
  private String accountNumber;

  @NotBlank(message = "El nombre del titular no puede estar vacío.")
  private String ownerName;

  @NotBlank(message = "El correo electrónico no puede estar vacío.")
  @Email(message = "El formato del correo electrónico no es válido.")
  private String email;

  @NotNull(message = "El tipo de cuenta no puede estar vacío.")
  private AccountType type;

  @PositiveOrZero(message = "El saldo no puede ser negativo.")
  private BigDecimal balance;
}
