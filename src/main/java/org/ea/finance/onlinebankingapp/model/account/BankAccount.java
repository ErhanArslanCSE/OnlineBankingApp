package org.ea.finance.onlinebankingapp.model.account;


public class BankAccount extends Account {

    public BankAccount(int id, int userid, String accountNumber, double balance) {
        setId(id);
        setUserid(userid);
        setAccountNumber(accountNumber);
        setAccountType(AccountType.BANK);
        setBalance(balance);
    }
}
