package org.ea.finance.onlinebankingapp.dao.impl;

import org.ea.finance.onlinebankingapp.dao.interfaces.GenericDAO;
import org.ea.finance.onlinebankingapp.util.DBConnectionUtil;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractGenericDAOImpl<T> implements GenericDAO<T> {
    protected Connection connection;

    public AbstractGenericDAOImpl() {
        this.connection = DBConnectionUtil.getConnection();
    }

}
