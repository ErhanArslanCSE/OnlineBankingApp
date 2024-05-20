package org.ea.finance.onlinebankingapp.service;

import org.ea.finance.onlinebankingapp.dao.impl.TransactionDAOImpl;
import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.transactions.Transaction;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TransactionService extends BaseService<Transaction> {
    private final TransactionDAOImpl transactionDAO;

    public TransactionService() {
        super(new TransactionDAOImpl());
        this.transactionDAO = (TransactionDAOImpl) dao;
    }

    @Override
    public void add(Transaction transaction) {
        super.add(transaction);
        updateBalance(transaction.getAccount().getId());
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) throws SQLException {
        return transactionDAO.getTransactionsByAccountId(accountId);
    }

    public void updateBalance(int accountId) {
        AccountService accountService = new AccountService();
        Account currentAcc = accountService.getById(accountId);
        try {
            double totalValue = 0;
            for (Transaction transaction : getTransactionsByAccountId(accountId)) {
                totalValue += transaction.getAmount();
            }
            currentAcc.setBalance(totalValue);
            accountService.update(currentAcc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
