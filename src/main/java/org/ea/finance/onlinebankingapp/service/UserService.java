package org.ea.finance.onlinebankingapp.service;

import org.ea.finance.onlinebankingapp.dao.impl.UserDAOImpl;
import org.ea.finance.onlinebankingapp.model.authentication.User;

import java.sql.SQLException;

public class UserService extends BaseService<User> {
    private final UserDAOImpl userDAO;

    public UserService() {
        super(new UserDAOImpl());
        this.userDAO = (UserDAOImpl) dao;
    }

    public User getUserByUsername(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }

    public boolean isUsernameAvailable(String username) {
        return userDAO.isUsernameAvailable(username);
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        return userDAO.authenticateUser(username, password);
    }
}
