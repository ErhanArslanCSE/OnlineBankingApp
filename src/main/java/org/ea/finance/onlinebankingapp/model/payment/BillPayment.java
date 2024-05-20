package org.ea.finance.onlinebankingapp.model.payment;

import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

import java.util.Date;

public class BillPayment extends Payment{
    public BillPayment(int id, double amount, Account account, Date date) {
        setId(id);
        setAmount(amount);
        setType(PaymentType.BILL_PAYMENT);
        setAccount(account);
        setDate(date);
    }
}
