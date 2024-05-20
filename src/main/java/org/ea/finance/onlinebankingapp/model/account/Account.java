package org.ea.finance.onlinebankingapp.model.account;

import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

import java.time.LocalDateTime;

public class Account extends AuditableEntity {

    private int id;

    private int userid;
    private String accountNumber;

    private AccountType accountType;

    private double balance;

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getUserid() {
        return this.userid;
    }


    public void setUserid(int id) {
        this.userid=id;
    }


    public String getAccountNumber() {
        return accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public void setAccountType(AccountType type)
    {
        this.accountType=type;
    };

    public AccountType getAccountType(){return this.accountType;}


    public double getBalance() {
        return balance;
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }

}
