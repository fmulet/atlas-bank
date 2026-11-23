package com.atlas.bank.atlas_bank.infrastructure.adapter.out.fraud;

import com.atlas.bank.atlas_bank.application.port.out.IFraudCheckPort;
import com.atlas.bank.atlas_bank.domain.model.shared.FraudCheckResult;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ExternalFraudCheckerAdapter
 */
@Component
@Slf4j
public class ExternalFraudCheckerAdapter implements IFraudCheckPort {

  @Override
  public FraudCheckResult check(Long accountId, BigDecimal amount) {
    ExternalFraudResponse response = callExternalApi(accountId, amount);

    log.info(
      "Respuesta del servicio de fraude - riesgo: {}, score: {}, recomendación:{}",
      response.getRiskLevel(),
      response.getScore(),
      response.getRecommendation()
    );
    if ("BLOCK".equals(response.getRecommendation())) {
      return FraudCheckResult.blocked(
        "Operación bloqueada por riesgo " +
          response.getRiskLevel() +
          " (score: " +
          response.getScore() +
          ")"
      );
    }
    return FraudCheckResult.allowed();
  }

  private ExternalFraudResponse callExternalApi(Long accountId, BigDecimal amount) {
    if (amount.compareTo(new BigDecimal("1000000")) > 0) {
      return new ExternalFraudResponse("HIGH", 0.95, "BLOCK");
    }
    return new ExternalFraudResponse("LOW", 0.1, "ALLOW");
  }
}
