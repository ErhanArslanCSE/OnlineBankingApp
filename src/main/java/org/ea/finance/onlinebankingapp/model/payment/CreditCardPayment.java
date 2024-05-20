package org.ea.finance.onlinebankingapp.model.payment;

import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

import java.util.Date;

public class CreditCardPayment extends Payment {
    public CreditCardPayment(int id, double amount, Account account,Account destinationAccount, Date date) {
        setId(id);
        setAmount(amount);
        setType(PaymentType.CREDIT_CARD);
        setAccount(account);
        setDestinationAccount(destinationAccount);
        setDate(date);
    }
}
