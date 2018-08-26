package main.java.by.epam.tattoo.dao;

import main.java.by.epam.tattoo.entity.Entity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public interface AbstractDao<T extends Entity> {
    Logger logger = LogManager.getLogger();

    T register(T object) throws DaoException;

    boolean delete(T object) throws DaoException;

    default void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Couldn't close statement: " + e.getMessage(), e);
            }
        }
    }
}
