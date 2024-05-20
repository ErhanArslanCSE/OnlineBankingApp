package org.ea.finance.onlinebankingapp.factory;

import org.ea.finance.onlinebankingapp.model.transactions.IncomeTransaction;
import org.ea.finance.onlinebankingapp.model.transactions.ExpenseTransaction;
import org.ea.finance.onlinebankingapp.model.transactions.TransactionType;
import org.ea.finance.onlinebankingapp.model.transactions.TransferTransaction;
import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.transactions.Transaction;
import org.ea.finance.onlinebankingapp.util.datavalidator.TransactionValidator;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.ValidationResult;

public class TransactionFactory {



    public static Transaction createTransaction(int id, TransactionType type, double amount, Account account,Account sourceAccount, Account destinationAccount,boolean existingTransaction ) {
        Transaction newT=null;
        switch (type) {
            case INCOME:
                newT = new IncomeTransaction(id, amount, account, sourceAccount, destinationAccount);
                break;
            case EXPENSE:
                newT = new ExpenseTransaction(id, amount, account, sourceAccount, destinationAccount);
                break;
            case TRANSFER:
                newT = new TransferTransaction(id, amount, account, sourceAccount, destinationAccount);
                break;
            default:
                throw new IllegalArgumentException("Invalid transaction type: " + type);
        }

        ValidationResult validationResult = new TransactionValidator().validateObject(newT, existingTransaction);
        if (!validationResult.isValid()) {
            throw new IllegalArgumentException(validationResult.getErrorMessage());
        }

        return newT;
    }

}
