package com.atlas.bank.atlas_bank.domain.model.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * MoneyDomainTest
 */
class MoneyDomainTest {

  @Test
  @DisplayName("Debe sumar dos montos en la misma moneda")
  void shouldAddSameCurrency() {
    Money money1 = Money.of(new BigDecimal("100"), Currency.CLP);
    Money money2 = Money.of(new BigDecimal("50"), Currency.CLP);
    Money result = money1.add(money2);
    assertEquals(Money.of(new BigDecimal("150"), Currency.CLP), result);
  }

  @Test
  @DisplayName("Debe rechazar operacion entre monedas diferentes")
  void shouldRejectDifferentCurrencies() {
    Money money1 = Money.of(new BigDecimal("100"), Currency.CLP);
    Money money2 = Money.of(new BigDecimal("5.50"), Currency.USD);
    assertThrows(IllegalArgumentException.class, () -> money1.add(money2));
  }

  @Test
  @DisplayName("Debe detectar monto negativo")
  void shouldDetectNegativeAmount() {
    Money money = Money.of(new BigDecimal("-100"), Currency.CLP);
    assertTrue(money.isNegative());
  }

  @Test
  @DisplayName("Debe comparar montos correctamente")
  void shouldCompareAmounts() {
    Money money1 = Money.of(new BigDecimal("100"), Currency.CLP);
    Money money2 = Money.of(new BigDecimal("50"), Currency.CLP);

    assertTrue(money1.isGreaterThan(money2));
    assertTrue(money2.isLessThan(money1));
  }

  @Test
  @DisplayName("Money.zero debe tener monto cero")
  void shouldCreateZeroMoney() {
    Money zero = Money.zero(Currency.CLP);
    assertEquals(new BigDecimal("0.00"), zero.getAmount());
    assertFalse(zero.isNegative());
  }
}
