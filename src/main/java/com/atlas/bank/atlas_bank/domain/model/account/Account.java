package com.atlas.bank.atlas_bank.domain.model.account;

import com.atlas.bank.atlas_bank.domain.exception.AccountNotActiveException;
import com.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import com.atlas.bank.atlas_bank.domain.model.shared.Currency;
import com.atlas.bank.atlas_bank.domain.model.shared.Email;
import com.atlas.bank.atlas_bank.domain.model.shared.Money;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Account
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Account {

  @EqualsAndHashCode.Include
  private Long id;

  private String accountNumber;

  private String ownerName;

  private Email email;

  private AccountType type;

  private Money balance;

  private AccountStatus status;

  private LocalDateTime createdAt;

  private Long customerId;

  public void deposit(Money amount) {
    if (amount.isNegative()) {
      throw new IllegalArgumentException("Monto no puede ser negativo");
    }
    this.balance = this.balance.add(amount);
  }

  public void withdraw(Money amount) {
    if (amount.isNegative()) {
      throw new IllegalArgumentException("Monto no puede ser negativo");
    }
    if (this.balance.isLessThan(amount)) {
      throw new InsufficientFundsException(id, balance.getAmount(), amount.getAmount());
    }
    this.balance = this.balance.subtract(amount);
  }

  public void close() {
    if (status != AccountStatus.ACTIVE) {
      throw new AccountNotActiveException(id, status == null ? "null" : status.name());
    }
    if (balance != null && !Money.zero(balance.getCurrency()).equals(balance)) {
      throw new IllegalStateException("No se puede cerrar una cuenta con saldo");
    }
    this.status = AccountStatus.CLOSED;
  }

  public void initDefaults() {
    if (status == null) status = AccountStatus.ACTIVE;
    if (balance == null) balance = Money.zero(Currency.CLP);
    if (createdAt == null) createdAt = LocalDateTime.now();
  }
}
