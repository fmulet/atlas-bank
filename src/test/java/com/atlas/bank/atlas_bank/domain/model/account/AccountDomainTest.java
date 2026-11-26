package com.atlas.bank.atlas_bank.domain.model.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.atlas.bank.atlas_bank.domain.exception.AccountNotActiveException;
import com.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import com.atlas.bank.atlas_bank.domain.model.shared.Currency;
import com.atlas.bank.atlas_bank.domain.model.shared.Email;
import com.atlas.bank.atlas_bank.domain.model.shared.Money;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * AccountDomainTest
 */
class AccountDomainTest {

  private Account createAccountWithBalance(BigDecimal balance) {
    return Account.builder()
      .id(1L)
      .accountNumber("ATL-0001-0001-0001")
      .ownerName("María García")
      .email(Email.of("maria@example.com"))
      .type(AccountType.SAVINGS)
      .balance(Money.of(balance, Currency.CLP))
      .status(AccountStatus.ACTIVE)
      .build();
  }

  @Test
  @DisplayName("Debe depostar dinero correctamente")
  void shouldDepositMoney() {
    Account account = createAccountWithBalance(new BigDecimal("1000"));
    Money deposit = Money.of(new BigDecimal("500"), Currency.CLP);
    account.deposit(deposit);

    assertEquals(Money.of(new BigDecimal("1500"), Currency.CLP), account.getBalance());
  }

  @Test
  @DisplayName("Debe retirar dinero cuando hay fondos suficientes")
  void shouldWithdrawWhenSufficientFunds() {
    Account account = createAccountWithBalance(new BigDecimal("1000"));
    Money withdrawal = Money.of(new BigDecimal("300"), Currency.CLP);

    account.withdraw(withdrawal);
    assertEquals(Money.of(new BigDecimal("700"), Currency.CLP), account.getBalance());
  }

  @Test
  @DisplayName("Debe rechazar dinero cuando no hay fondos suficientes")
  void shouldRejectWithdrawWhenInsufficientFunds() {
    Account account = createAccountWithBalance(new BigDecimal("100"));
    Money withdrawal = Money.of(new BigDecimal("500"), Currency.CLP);

    assertThrows(InsufficientFundsException.class, () -> account.withdraw(withdrawal));
  }

  @Test
  @DisplayName("Debe rechazar depósito con monto negativo")
  void shouldRejectNegativeDeposit() {
    Account account = createAccountWithBalance(new BigDecimal("1000"));
    Money negative = Money.of(new BigDecimal("-100"), Currency.CLP);

    assertThrows(IllegalArgumentException.class, () -> account.deposit(negative));
  }

  @Test
  @DisplayName("Debe inicializar con valores por defecto")
  void shouldInitializeDefaults() {
    Account account = Account.builder()
      .accountNumber("ATL-0002-0002-0002")
      .ownerName("Juan Pérez")
      .email(Email.of("juan@example.com"))
      .type(AccountType.CHECKING)
      .build();

    account.initDefaults();
    assertEquals(AccountStatus.ACTIVE, account.getStatus());
    assertEquals(Money.zero(Currency.CLP), account.getBalance());
    assertNotNull(account.getCreatedAt());
  }

  @Test
  @DisplayName("Debe cerrar una cuenta activa sin saldo")
  void shouldCloseActiveAccountWithZeroBalance() {
    Account account = createAccountWithBalance(BigDecimal.ZERO);

    account.close();

    assertEquals(AccountStatus.CLOSED, account.getStatus());
  }

  @Test
  @DisplayName("Debe rechazar el cierre cuando la cuenta no está activa")
  void shouldRejectCloseWhenAccountIsNotActive() {
    Account account = createAccountWithBalance(BigDecimal.ZERO);
    account.setStatus(AccountStatus.FROZEN);

    assertThrows(AccountNotActiveException.class, () -> account.close());
  }

  @Test
  @DisplayName("Debe rechazar el cierre cuando la cuenta tiene saldo")
  void shouldRejectCloseWhenAccountHasBalance() {
    Account account = createAccountWithBalance(new BigDecimal("1000"));

    IllegalStateException exception = assertThrows(
      IllegalStateException.class,
      () -> account.close()
    );
    assertEquals("No se puede cerrar una cuenta con saldo", exception.getMessage());
  }
}
