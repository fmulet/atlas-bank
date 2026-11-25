package com.atlas.bank.atlas_bank.domain.model.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.atlas.bank.atlas_bank.domain.model.shared.Currency;
import com.atlas.bank.atlas_bank.domain.model.shared.Email;
import com.atlas.bank.atlas_bank.domain.model.shared.Money;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * AccountDomainTest
 */
public class AccountDomainTest {

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
}
