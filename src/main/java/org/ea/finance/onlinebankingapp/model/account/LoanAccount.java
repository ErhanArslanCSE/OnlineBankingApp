package org.ea.finance.onlinebankingapp.model.account;

import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

public class LoanAccount extends Account  {

    public LoanAccount(int id, int userid,String accountNumber, double balance) {
        setId(id);
        setUserid(userid);
        setAccountNumber(accountNumber);
        this.setAccountType(AccountType.LOAN);
        setBalance(balance);
    }

}
