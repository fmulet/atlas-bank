package com.atlas.bank.atlas_bank.application.port.in;

import com.atlas.bank.atlas_bank.application.query.GetAccountStatementQuery;
import com.atlas.bank.atlas_bank.application.query.TransactionReadModel;
import java.util.List;

/**
 * IGetTransactionsByAccountUseCase
 */
public interface IGetTransactionsByAccountUseCase {
  List<TransactionReadModel> getByAccountId(GetAccountStatementQuery query);
}
