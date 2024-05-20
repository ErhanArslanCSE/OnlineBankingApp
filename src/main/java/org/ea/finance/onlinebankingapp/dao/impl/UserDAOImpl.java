package org.ea.finance.onlinebankingapp.dao.impl;

import org.ea.finance.onlinebankingapp.factory.UserFactory;
import org.ea.finance.onlinebankingapp.model.authentication.User;
import org.ea.finance.onlinebankingapp.model.authentication.UserType;

import java.sql.*;
import java.util.List;

public class UserDAOImpl extends AbstractGenericDAOImpl<User> {

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (username, first_name, last_name, email, password, user_type, created_at, updated_at, is_admin_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setString(6, String.valueOf(user.getUserType()));
            ps.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));
            ps.setTimestamp(8, Timestamp.valueOf(user.getUpdatedAt()));
            ps.setString(9, user.getIsAdminUser());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET first_name=?, last_name=?, updated_at=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setTimestamp(3, Timestamp.valueOf(user.getUpdatedAt()));
            ps.setInt(4, user.getUserid());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int userId) {
        String sql = "DELETE FROM users WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public User getById(int userId) {
        String sql = "SELECT * FROM users WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return createUserFromResultSet(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return createUserFromResultSet(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean isUsernameAvailable(String username) {
        String sql = "SELECT COUNT(*) AS count FROM users WHERE username=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count == 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM users WHERE username=? AND password=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            User user = UserFactory.createUser(rs.getString("username"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    UserType.valueOf(rs.getString("user_type")),
                    rs.getString("is_admin_user"));
            user.setUserid(rs.getInt("userid"));
            return user;
        }
        return null;
    }
}
