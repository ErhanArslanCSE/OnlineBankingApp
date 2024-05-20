package org.ea.finance.onlinebankingapp.service;

import org.ea.finance.onlinebankingapp.dao.impl.AccountDAOImpl;
import org.ea.finance.onlinebankingapp.factory.TransactionFactory;
import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.transactions.Transaction;
import org.ea.finance.onlinebankingapp.model.transactions.TransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccountService extends BaseService<Account> {
    private final AccountDAOImpl accountDAO;

    public AccountService() {
        super(new AccountDAOImpl());
        this.accountDAO = (AccountDAOImpl) dao;
    }

    public void createAccount(Account account) {
        Transaction openingTransaction = TransactionFactory.createTransaction(
                -1,
                TransactionType.INCOME,
                account.getBalance(),
                account,
                account,
                null,
                false
        );

        dao.add(account);
        account.setId(accountDAO.getAccountByUseridAndAccountNumber(account.getUserid(), account.getAccountNumber()));
        // Assuming you have a transaction service to handle transactions
        TransactionService transactionService = new TransactionService();
        transactionService.add(openingTransaction);
    }

    public List<Account> getAccountByUserid(int userid) {
        return accountDAO.getAccountByUserid(userid);
    }

    public int getAccountByUseridAndAccountNumber(int userid, String accountNumber) {
        return accountDAO.getAccountByUseridAndAccountNumber(userid, accountNumber);
    }

    public static String generateUniqueAccountValue() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
        return now.format(formatter);
    }
}
