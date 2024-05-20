package org.ea.finance.onlinebankingapp.factory;

import org.ea.finance.onlinebankingapp.dao.impl.AccountDAOImpl;
import org.ea.finance.onlinebankingapp.model.payment.*;
import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.service.AccountService;
import org.ea.finance.onlinebankingapp.util.datavalidator.PaymentValidator;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.ValidationResult;

import java.security.InvalidParameterException;
import java.util.Date;

public class PaymentFactory {
    public static Payment createPayment(int id, double amount, PaymentType type, int accountId, int destinationAccountId, Date date,boolean existingPayment) {
        Payment newP = null;
        AccountService accountService = new AccountService();
        Account account = accountService.getById(accountId);
        Account destinationAccount = destinationAccountId > 0 ? accountService.getById(destinationAccountId) : null;

        switch (type) {
            case PAYPAL:
                newP = new PayPalPayment(id, amount, account, date);
                break;
            case BANK_TRANSFER:
                newP = new BankTransferPayment(id, amount, account, destinationAccount, date);
                break;
            case CREDIT_CARD:
                newP = new CreditCardPayment(id, amount, account, destinationAccount, date);
                break;
            case BILL_PAYMENT:
                newP = new BillPayment(id, amount, account, date);
                break;
            default:
                throw new InvalidParameterException("Unsupported payment type: " + type);
        }

        PaymentValidator validator = new PaymentValidator();
        ValidationResult validationResult = validator.validateObject(newP, existingPayment);
        if (!validationResult.isValid()) {
            throw new IllegalArgumentException(validationResult.getErrorMessage());
        }

        return newP;

    }
}
