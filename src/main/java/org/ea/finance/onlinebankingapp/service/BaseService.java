package org.ea.finance.onlinebankingapp.service;

import org.ea.finance.onlinebankingapp.dao.interfaces.GenericDAO;

import java.util.List;

public abstract class BaseService<T> {
    protected final GenericDAO<T> dao;

    protected BaseService(GenericDAO<T> dao) {
        this.dao = dao;
    }

    public void add(T obj) {
        dao.add(obj);
    }

    public void update(T obj) {
        dao.update(obj);
    }

    public T getById(int id) {
        return dao.getById(id);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    public List<T> getAll() {
        return dao.getAll();
    }
}
