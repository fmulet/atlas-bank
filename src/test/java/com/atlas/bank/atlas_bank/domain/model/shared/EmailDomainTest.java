package com.atlas.bank.atlas_bank.domain.model.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * EmailDomainTest
 */
class EmailDomainTest {

  @Test
  @DisplayName("Debe crear email con formato válido")
  void shouldCreateValidEmail() {
    // Arrange
    Email email = Email.of("test@example.com");

    assertEquals("test@example.com", email.getValue());
  }

  @Test
  @DisplayName("Debe normalizar a minúsculas")
  void shouldNormalizeLowerCase() {
    // Arrange
    Email email = Email.of("TEST@EXAMPLE.COM");

    assertEquals("test@example.com", email.getValue());
  }

  @Test
  @DisplayName("Debe rechazar email con formato inválido")
  void shouldRejectInvalidFormat() {
    assertThrows(IllegalArgumentException.class, () -> Email.of("test@example"));
  }

  @Test
  @DisplayName("Debe rechazar si el email es nulo")
  void shouldRejectNullEmail() {
    assertThrows(IllegalArgumentException.class, () -> Email.of(null));
  }
}
