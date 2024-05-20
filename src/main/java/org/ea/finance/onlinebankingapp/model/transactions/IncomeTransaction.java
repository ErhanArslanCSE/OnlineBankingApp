package org.ea.finance.onlinebankingapp.model.transactions;

import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

public class IncomeTransaction extends Transaction {
    public IncomeTransaction(int id, double amount, Account account,Account sourceAccount, Account destinationAccount) {
        setId(id);
        setAmount(amount);
        setType(TransactionType.INCOME);
        setAccount(account);
        setSourceAccount(sourceAccount);
        setDestinationAccount(destinationAccount);
    }
}
