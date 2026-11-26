package com.atlas.bank.atlas_bank.application.command;

import lombok.Builder;

/**
 * CloseAccountCommand
 */
@Builder
public record CloseAccountCommand(Long accountId) {}
