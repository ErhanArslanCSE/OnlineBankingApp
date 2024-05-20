package org.ea.finance.onlinebankingapp.dao.impl;

import org.ea.finance.onlinebankingapp.factory.TransactionFactory;
import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.transactions.Transaction;
import org.ea.finance.onlinebankingapp.model.transactions.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionDAOImpl extends AbstractGenericDAOImpl<Transaction> {

    @Override
    public void add(Transaction transaction) {
        String sql = "INSERT INTO transactions (amount, type, account_id, source_account_id, destination_account_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, transaction.getAmount());
            ps.setString(2, transaction.getType().toString());
            ps.setInt(3, transaction.getAccount().getId());
            ps.setInt(4, transaction.getSourceAccount().getId());
            ps.setInt(5, transaction.getDestinationAccount() != null ? transaction.getDestinationAccount().getId() : -1);
            ps.setTimestamp(6, Timestamp.valueOf(transaction.getCreatedAt()));
            ps.setTimestamp(7, Timestamp.valueOf(transaction.getUpdatedAt()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Transaction transaction) {
        String sql = "UPDATE transactions SET amount=?, type=?, account_id=?, source_account_id=?, destination_account_id=?, updated_at=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, transaction.getAmount());
            ps.setString(2, transaction.getType().toString());
            ps.setInt(3, transaction.getAccount().getId());
            ps.setInt(4, transaction.getSourceAccount().getId());
            ps.setInt(5, transaction.getDestinationAccount().getId());
            ps.setTimestamp(6, Timestamp.valueOf(transaction.getUpdatedAt()));
            ps.setInt(7, transaction.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int transactionId) {
        String sql = "DELETE FROM transactions WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, transactionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Transaction getById(int transactionId) {
        String sql = "SELECT * FROM transactions WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createTransactionFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transaction> getAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Transaction transaction = createTransactionFromResultSet(rs);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = createTransactionFromResultSet(rs);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Utility method to create Transaction objects from ResultSet
    private Transaction createTransactionFromResultSet(ResultSet rs) throws SQLException {
        Account account = getAccountById(rs.getInt("account_id"));
        Account sourceAccount = getAccountById(rs.getInt("source_account_id"));
        Account destinationAccount = getAccountById(rs.getInt("destination_account_id"));
        TransactionType type = TransactionType.valueOf(rs.getString("type"));

        return TransactionFactory.createTransaction(
                rs.getInt("id"),
                type,
                rs.getDouble("amount"),
                account,
                sourceAccount,
                destinationAccount,
                true
                );
    }

    private Account getAccountById(int accountId) throws SQLException {
        // Use AccountDAOImpl to retrieve the account by its ID
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        return accountDAO.getById(accountId);
    }



}