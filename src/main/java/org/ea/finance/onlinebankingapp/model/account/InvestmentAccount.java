package org.ea.finance.onlinebankingapp.model.account;

import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

public class InvestmentAccount   extends Account {

    public InvestmentAccount(int id, int userid,  String accountNumber, double balance) {
        setId(id);
        setUserid(userid);
        setAccountNumber(accountNumber);
        setAccountType(AccountType.INVESTMENT);
        setBalance(balance);
    }

}
