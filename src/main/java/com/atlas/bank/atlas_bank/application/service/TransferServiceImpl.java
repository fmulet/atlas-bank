package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.application.command.TransferMoneyCommand;
import com.atlas.bank.atlas_bank.application.port.in.ITransferMoneyUseCase;
import com.atlas.bank.atlas_bank.application.port.out.IAccountRepositoryPort;
import com.atlas.bank.atlas_bank.application.port.out.ITransactionRepositoryPort;
import com.atlas.bank.atlas_bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import com.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import com.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;
import com.atlas.bank.atlas_bank.domain.service.TransferDomainService;
import com.atlas.bank.atlas_bank.domain.strategy.fee.IFeeCalculator;
import com.atlas.bank.atlas_bank.domain.validation.ITransferValidator;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TransactionService
 */
@Service
public class TransferServiceImpl
  extends TransactionProcessor<TransferContext>
  implements ITransferMoneyUseCase
{

  private final IAccountRepositoryPort accountRepository;
  private final List<IFeeCalculator> feeCalculators;

  private final List<ITransferValidator> validators;
  private final TransferDomainService transferDomainService;

  public TransferServiceImpl(
    ITransactionRepositoryPort transactionRepository,
    IAccountRepositoryPort accountRepository,
    List<IFeeCalculator> feeCalculators,
    List<ITransferValidator> validators,
    TransferDomainService transferDomainService
  ) {
    super(transactionRepository);
    this.accountRepository = accountRepository;
    this.feeCalculators = feeCalculators;
    this.validators = validators;
    this.transferDomainService = transferDomainService;
  }

  @Override
  protected void validate(TransferContext context) {
    validators.forEach(validator -> validator.validate(context));
  }

  @Override
  protected BigDecimal calculateFee(TransferContext context) {
    return feeCalculators
      .stream()
      .filter(fc -> fc.supports(context.from().getType()))
      .findFirst()
      .orElseThrow(() ->
        new RuntimeException(
          "No se encontró un calculador de comisiones para el tipo de cuenta de origen " +
            context.from().getType()
        )
      )
      .calculateFee(context.amount());
  }

  @Override
  protected void execute(TransferContext context, BigDecimal fee) {
    transferDomainService.transfer(context.from(), context.to(), context.amount(), fee);

    accountRepository.save(context.from());
    accountRepository.save(context.to());
  }

  @Override
  protected Transaction save(TransferContext context, BigDecimal fee) {
    Transaction transaction = TransactionFactory.createTransfer(context, fee);
    return transactionRepository.save(transaction);
  }

  @Override
  @Transactional
  public Transaction execute(TransferMoneyCommand command) {
    Account from = accountRepository
      .findById(command.fromId())
      .orElseThrow(() -> new AccountNotFoundException(command.fromId()));
    Account to = accountRepository
      .findById(command.toId())
      .orElseThrow(() -> new AccountNotFoundException(command.toId()));
    Transaction transaction = process(new TransferContext(from, to, command.amount()));
    transaction.executeTransfer();
    transactionRepository.save(transaction);

    return transaction;
  }
}
