package com.atlas.bank.atlas_bank.domain.model.shared;

/**
 * FraudCheckResult
 */
public record FraudCheckResult(boolean blocked, String reason) {
  public static FraudCheckResult allowed() {
    return new FraudCheckResult(false, null);
  }

  public static FraudCheckResult blocked(String reason) {
    return new FraudCheckResult(true, reason);
  }
}
