package org.ea.finance.onlinebankingapp.model.transactions;

import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

import java.time.LocalDateTime;

public class Transaction extends AuditableEntity {
    // İşlemin ID'si
    private int id;

    // İşlem miktarı
    private double amount;

    // İşlem türü
    private TransactionType type;

    // İşlem yapıldığı hesap
    private Account account;

    private Account sourceAccount;
    private Account destinationAccount;
    
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

    
    public TransactionType getType() {
        return type;
    }

    
    public void setType(TransactionType type) {
        this.type = type;
    }

    
    public Account getAccount() {
        return account;
    }

    
    public void setAccount(Account account) {
        this.account = account;
    }

    
    public Account getSourceAccount() {
        return sourceAccount;
    }

    
    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    
    public Account getDestinationAccount() {
        return destinationAccount;
    }

    
    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}
