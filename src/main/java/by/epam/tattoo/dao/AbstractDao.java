package main.java.by.epam.tattoo.dao;

import main.java.by.epam.tattoo.entity.Entity;

public interface AbstractDao<T extends Entity> {
    T register(T object) throws DaoException;
    boolean delete(T object) throws DaoException;
}
