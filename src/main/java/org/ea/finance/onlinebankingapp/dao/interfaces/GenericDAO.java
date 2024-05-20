package org.ea.finance.onlinebankingapp.dao.interfaces;

import java.util.List;

public interface GenericDAO<T> {
    void add(T t);
    void update(T t);
    void delete(int id);
    T getById(int id);
    List<T> getAll();
}
