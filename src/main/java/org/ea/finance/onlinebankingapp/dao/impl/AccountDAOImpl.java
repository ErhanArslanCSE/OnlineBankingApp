package org.ea.finance.onlinebankingapp.dao.impl;

import org.ea.finance.onlinebankingapp.factory.AccountFactory;
import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.account.AccountType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl extends AbstractGenericDAOImpl<Account> {

    @Override
    public void add(Account account) {
        String sql = "INSERT INTO accounts (account_number, account_type, balance, userid, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getAccountNumber());
            ps.setString(2, String.valueOf(account.getAccountType()));
            ps.setDouble(3, account.getBalance());
            ps.setInt(4, account.getUserid());
            ps.setTimestamp(5, Timestamp.valueOf(account.getCreatedAt()));
            ps.setTimestamp(6, Timestamp.valueOf(account.getUpdatedAt()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE accounts SET account_number=?, account_type=?, balance=?, updated_at=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getAccountNumber());
            ps.setString(2, String.valueOf(account.getAccountType()));
            ps.setDouble(3, account.getBalance());
            ps.setTimestamp(4, Timestamp.valueOf(account.getUpdatedAt()));
            ps.setInt(5, account.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int accountId) {
        String sql = "DELETE FROM accounts WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Account getById(int accountId) {
        Account account = null;
        String sql = "SELECT * FROM accounts WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = createAccountFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return account;
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Account account = createAccountFromResultSet(rs);
                accounts.add(account);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return accounts;
    }

    public List<Account> getAccountByUserid(int userid) {
        List<Account> userAccounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE userid = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = createAccountFromResultSet(rs);
                userAccounts.add(account);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userAccounts;
    }

    public int getAccountByUseridAndAccountNumber(int userid, String accountNumber) {
        String sql = "SELECT id FROM accounts WHERE userid = ? AND account_number = ?";
        int id = -1;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userid);
            ps.setString(2, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    private Account createAccountFromResultSet(ResultSet rs) throws SQLException {
        return AccountFactory.createAccount(
                rs.getInt("id"),
                rs.getInt("userid"),
                rs.getString("account_number"),
                AccountType.valueOf(rs.getString("account_type")),
                rs.getDouble("balance"),
                true
        );

    }
}
