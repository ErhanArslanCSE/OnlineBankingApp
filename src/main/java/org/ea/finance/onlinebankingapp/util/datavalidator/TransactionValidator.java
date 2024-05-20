package org.ea.finance.onlinebankingapp.util.datavalidator;

import org.ea.finance.onlinebankingapp.model.transactions.Transaction;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.ValidationResult;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.Validator;

public class TransactionValidator  extends Validator {

    @Override
    public ValidationResult validateObject(Object  transaction,boolean existingObject) {
        if (!existingObject) {
            switch (((Transaction)transaction).getType()) {
                case INCOME:
                    if (((Transaction)transaction).getAmount() < 0) {
                        return new ValidationResult(false, "Income amount cannot be negative.");
                    }
                    break;
                case EXPENSE:
                    if (((Transaction)transaction).getAmount() > 0) {
                        return new ValidationResult(false, "Expense amount cannot be positive.");
                    }
                    break;
                case TRANSFER:
                    if (((Transaction)transaction).getAmount() < 0 && ((Transaction)transaction).getAccount().getId()!=((Transaction)transaction).getSourceAccount().getId()) {
                        return new ValidationResult(false, "Transfer amount cannot be negative.");
                    }
                    if (((Transaction)transaction).getDestinationAccount() == null) {
                        return new ValidationResult(false, "Invalid target account for transfer.");
                    }
                    break;
                default:
                    return new ValidationResult(false, "Invalid transaction type: " + ((Transaction)transaction).getType());
            }
        }
        return new ValidationResult(true, "Transaction is valid.");
    }
}

