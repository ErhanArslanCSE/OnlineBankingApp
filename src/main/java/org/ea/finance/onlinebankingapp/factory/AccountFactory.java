package org.ea.finance.onlinebankingapp.factory;

import org.ea.finance.onlinebankingapp.model.account.*;
import org.ea.finance.onlinebankingapp.util.datavalidator.AccountValidator;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.ValidationResult;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.Validator;

public class AccountFactory {
    public static Account createAccount(int id, int userid, String accountNumber, AccountType accountType, double balance,boolean existingAccount) {
        Account newA=null;
        switch (accountType) {
            case BANK:
                newA= new BankAccount(id, userid,accountNumber, balance);
                break;
            case CREDITCARD:
                newA= new CreditCardAccount(id, userid, accountNumber, balance);
                break;
            case INVESTMENT:
                newA= new InvestmentAccount(id, userid,accountNumber, balance);
                break;
            case LOAN:
                newA= new LoanAccount(id, userid,accountNumber, balance);
                break;
            default:
                break;
        }
        ValidationResult validationResult=new AccountValidator().validateObject(newA,existingAccount);
        if(validationResult.isValid())
        {
            return newA;
        }
        else
        {
            throw new IllegalArgumentException(validationResult.getErrorMessage());
        }
    }
}
