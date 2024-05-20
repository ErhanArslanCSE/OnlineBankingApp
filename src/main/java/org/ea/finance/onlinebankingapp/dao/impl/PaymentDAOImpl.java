package org.ea.finance.onlinebankingapp.dao.impl;


import org.ea.finance.onlinebankingapp.factory.PaymentFactory;
import org.ea.finance.onlinebankingapp.model.payment.Payment;
import org.ea.finance.onlinebankingapp.model.payment.PaymentType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl extends AbstractGenericDAOImpl<Payment> {

    @Override
    public void add(Payment payment) {
        String sql = "INSERT INTO payments (amount, type, account_id, target_account_id, date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, payment.getAmount());
            ps.setString(2, payment.getType().toString());
            ps.setInt(3, payment.getAccount().getId());
            ps.setInt(4, payment.getDestinationAccount() != null ? payment.getDestinationAccount().getId() : -1);
            ps.setDate(5, new java.sql.Date(payment.getDate().getTime()));
            ps.setTimestamp(6, Timestamp.valueOf(payment.getCreatedAt()));
            ps.setTimestamp(7, Timestamp.valueOf(payment.getUpdatedAt()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Payment payment) {
        String sql = "UPDATE payments SET amount=?, type=?, account_id=?, target_account_id=?, date=?, updated_at=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, payment.getAmount());
            ps.setString(2, payment.getType().toString());
            ps.setInt(3, payment.getAccount().getId());
            ps.setInt(4, payment.getDestinationAccount().getId());
            ps.setDate(5, new java.sql.Date(payment.getDate().getTime()));
            ps.setTimestamp(6, Timestamp.valueOf(payment.getUpdatedAt()));
            ps.setInt(7, payment.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int paymentId) {
        String sql = "DELETE FROM payments WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, paymentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Payment getById(int paymentId) {
        String sql = "SELECT * FROM payments WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, paymentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createPaymentFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Payment> getAll() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Payment payment = createPaymentFromResultSet(rs);
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public List<Payment> getPaymentsByAccountAndType(int accountId, PaymentType type) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE account_id=? AND type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setString(2, type.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment payment = createPaymentFromResultSet(rs);
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    // Utility method to create Payment objects from ResultSet
    private Payment createPaymentFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double amount = rs.getDouble("amount");
        PaymentType type = PaymentType.valueOf(rs.getString("type"));
        int accountId = rs.getInt("account_id");
        int targetAccountId = rs.getInt("target_account_id");
        Date date = rs.getDate("date");
        return PaymentFactory.createPayment(id, amount, type, accountId, targetAccountId, date,true);
    }
}
