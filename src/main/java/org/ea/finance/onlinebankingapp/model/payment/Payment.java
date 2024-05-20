package org.ea.finance.onlinebankingapp.model.payment;

import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

import java.time.LocalDateTime;
import java.util.Date;

public class Payment  extends AuditableEntity {
    private int id;

    private double amount;

    private PaymentType type;

    private Account account;

    private Account destinationAccount;

    private Date date;

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public double getAmount() {
        return amount;
    }


    public void setAmount(double amount) {
        this.amount = amount;
    }


    public PaymentType getType() {
        return type;
    }

    protected void setType(PaymentType type) {
        this.type = type;
    }


    public Account getAccount() {
        return account;
    }


    public void setAccount(Account account) {
        this.account = account;
    }


    public Account getDestinationAccount() {
        return destinationAccount;
    }


    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }
}
