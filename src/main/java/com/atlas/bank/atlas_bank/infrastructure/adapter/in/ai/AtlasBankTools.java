package com.atlas.bank.atlas_bank.infrastructure.adapter.in.ai;

import com.atlas.bank.atlas_bank.application.command.CloseAccountCommand;
import com.atlas.bank.atlas_bank.application.command.TransferMoneyCommand;
import com.atlas.bank.atlas_bank.application.port.in.CloseAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.IGetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.ITransferMoneyUseCase;
import com.atlas.bank.atlas_bank.domain.exception.AccountNotActiveException;
import com.atlas.bank.atlas_bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * AtlasBankTools
 */
@Component
@RequiredArgsConstructor
public class AtlasBankTools {

  private final ITransferMoneyUseCase transferMoneyUseCase;
  private final IGetAccountUseCase getAccountUseCase;
  private final CloseAccountUseCase closeAccountUseCase;

  @Tool(description = "Transferir dinero entre dos cuentas del banco atlas-bank")
  public String transferMoney(
    @ToolParam(description = "ID de la cuenta origen") String sourceAccountId,
    @ToolParam(description = "ID de la cuenta destino") String targetAccountId,
    @ToolParam(description = "Monto a transferir") BigDecimal amount
  ) {
    try {
      var command = TransferMoneyCommand.builder()
        .fromId(Long.parseLong(sourceAccountId))
        .toId(Long.parseLong(targetAccountId))
        .amount(amount)
        .build();
      transferMoneyUseCase.execute(command);
      return "Transferencia exitosa. Monto: $" + amount;
    } catch (InsufficientFundsException e) {
      return (
        "No se pudo realizar la transferencia, fondos insuficientes. Error: " +
        e.getMessage()
      );
    } catch (Exception e) {
      return "Error al realizar la transferencia: " + e.getMessage();
    }
  }

  @Tool(description = "Obtener el saldo de una cuenta del banco atlas-bank")
  public String getAccountBalance(
    @ToolParam(description = "ID de la cuenta a consultar") String accountId
  ) {
    try {
      var account = getAccountUseCase.findById(Long.parseLong(accountId));
      return "Saldo de la cuenta: $" + account.getBalance();
    } catch (AccountNotFoundException e) {
      return "No se encontró la cuenta con ID: " + accountId;
    } catch (Exception e) {
      return "Error al obtener el saldo de la cuenta: " + e.getMessage();
    }
  }

  @Tool(description = "Cerrar una cuenta del banco atlas-bank")
  public String closeAccount(
    @ToolParam(description = "ID de la cuenta a cerrar") String accountId
  ) {
    try {
      var command = CloseAccountCommand.builder()
        .accountId(Long.parseLong(accountId))
        .build();
      closeAccountUseCase.close(command);
      return "Cuenta cerrada exitosamente. ID: " + accountId;
    } catch (AccountNotFoundException e) {
      return "No se encontró la cuenta con ID: " + accountId;
    } catch (AccountNotActiveException e) {
      return "No se puede cerrar la cuenta: " + e.getMessage();
    } catch (IllegalStateException e) {
      return "No se puede cerrar la cuenta: " + e.getMessage();
    } catch (Exception e) {
      return "Error al cerrar la cuenta: " + e.getMessage();
    }
  }
}
