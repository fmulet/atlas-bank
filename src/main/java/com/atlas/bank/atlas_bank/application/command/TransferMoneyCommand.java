package com.atlas.bank.atlas_bank.application.command;

import java.math.BigDecimal;
import lombok.Builder;

/**
 * TransferMoneyCommand
 */
@Builder
public record TransferMoneyCommand(Long fromId, Long toId, BigDecimal amount) {}
