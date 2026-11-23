package com.atlas.bank.atlas_bank.infrastructure.adapter.out.fraud;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ExternalFraudResponse
 */
@Getter
@AllArgsConstructor
public class ExternalFraudResponse {

  private String riskLevel;
  private double score;
  private String recommendation;
}
