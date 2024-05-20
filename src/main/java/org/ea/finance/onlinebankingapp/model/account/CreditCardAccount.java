package org.ea.finance.onlinebankingapp.model.account;

import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

public class CreditCardAccount  extends Account {

    public CreditCardAccount(int id, int userid, String accountNumber, double balance) {
        setId(id);
        setUserid(userid);
        setAccountNumber(accountNumber);
        setAccountType(AccountType.CREDITCARD);
        setBalance(balance);
    }
}
