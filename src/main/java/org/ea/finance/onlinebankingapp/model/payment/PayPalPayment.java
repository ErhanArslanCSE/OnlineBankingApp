package org.ea.finance.onlinebankingapp.model.payment;

import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

import java.util.Date;

public class PayPalPayment extends Payment {
    public PayPalPayment(int id, double amount, Account account, Date date) {
        setId(id);
        setAmount(amount);
        setType(PaymentType.PAYPAL);
        setAccount(account);
        setDate(date);
    }
}
