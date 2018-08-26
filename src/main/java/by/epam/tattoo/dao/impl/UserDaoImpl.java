package main.java.by.epam.tattoo.dao.impl;


import main.java.by.epam.tattoo.connection.ConnectionPool;
import main.java.by.epam.tattoo.connection.ConnectionPoolException;
import main.java.by.epam.tattoo.connection.ProxyConnection;
import main.java.by.epam.tattoo.dao.AbstractDao;
import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.entity.Role;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.util.ConstantHeap;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements AbstractDao<User> {
    private final ConnectionPool pool;
    private static Logger logger = LogManager.getLogger();
    private static final String SQL_CHECK_USER_MATCHES =
            "SELECT login, password FROM users WHERE login =? AND password = SHA1(?) AND state_id=1";

    private static final String SQL_CHECK_LOGIN_EXISTS =
            "SELECT login FROM users WHERE login =?";

    private static final String SQL_CHECK_EMAIL_EXISTS =
            "SELECT email FROM users WHERE email =?";

    private static final String SQL_INSERT_USER =
            "INSERT INTO users(email, login, password) VALUES (?, ?, SHA1(?))";

    private static final String SQL_UPDATE_USER_DISCOUNT =
            "UPDATE users SET discount_pct = ? WHERE user_id = ?";

    private static final String SQL_UPDATE_USER_ROLE =
            "UPDATE users SET role_id = ? WHERE user_id = ?";

    private static final String SQL_CHANGE_USER_PASSWORD =
            "UPDATE users SET password = SHA1(?) WHERE login = ?";

    private static final String SQL_FIND_USER_BY_LOGIN =
            "SELECT user_id, email, login, password, role_name, discount_pct\n" +
                    "FROM users\n" +
                    "JOIN roles ON users.role_id = roles.roles_id \n" +
                    "WHERE login = ?";
    private static final String SQL_FIND_USER_BY_ID =
            "SELECT user_id, email, login, password, role_name, discount_pct\n" +
                    "FROM users\n" +
                    "JOIN roles ON users.role_id = roles.roles_id \n" +
                    "WHERE user_id = ?";
    private static final String SQL_SELECT_ALL_USERS =
            "SELECT user_id, email, login, password, role_name, discount_pct\n" +
                    "FROM users\n" +
                    "JOIN roles ON users.role_id = roles.roles_id\n" +
                    "WHERE state_id = 1\n" +
                    "ORDER BY login";

    private static final String SQL_CHANGE_STATUS_TO_INACTIVE =
            "UPDATE users SET state_id = 2 WHERE user_id =?";

    private static final String SQL_FIND_ROLE_ID_BY_NAME =
            "SELECT roles_id FROM roles WHERE role_name = ?";


    public UserDaoImpl() {
        pool = ConnectionPool.getInstance();
    }

    public User register(User user) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            String email = user.getEmail();
            String login = user.getLogin();
            String password = user.getPassword();
            if (emailExists(email) || loginExists(login)) {
                return null;
            }
            preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
            return findUserByLogin(connection, login);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    private boolean loginExists(String email) throws DaoException {
        return checkParameterExist(email, SQL_CHECK_LOGIN_EXISTS);
    }

    private boolean emailExists(String email) throws DaoException {
        return checkParameterExist(email, SQL_CHECK_EMAIL_EXISTS);
    }

    private boolean checkParameterExist(String email, String sqlCheckLoginExists) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        ProxyConnection connection = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(sqlCheckLoginExists);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }


    public User login(User user) throws DaoException {
        ProxyConnection connection = null;
        try {
            connection = pool.takeConnection();
            String login = user.getLogin();
            String password = user.getPassword();
            if (userMatches(login, password)) {
                return findUserByLogin(connection, login);
            } else {
                return null;
            }
        } finally {
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }


    private boolean userMatches(String login, String password) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        ProxyConnection connection = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_CHECK_USER_MATCHES);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    public boolean updateDiscount(int userId, int pct) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_DISCOUNT);
            preparedStatement.setInt(1, pct);
            preparedStatement.setInt(2, userId);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    public boolean updateRole(int userId, String role) throws DaoException {
        PreparedStatement preparedStatement = null;
        ProxyConnection connection = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_ROLE);
            preparedStatement.setInt(1, findUserRoleIdByName(role));
            preparedStatement.setInt(2, userId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }


    public ArrayList<User> findAllUsers() throws DaoException {
        ArrayList<User> users = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(createUserFromQueryResult(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    private User findUserByLogin(ProxyConnection connection, String login) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createUserFromQueryResult(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    public User findUserById(int id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createUserFromQueryResult(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    public User changeUserPassword(User user, String newPassword) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            if (userMatches(user.getLogin(), user.getPassword())) {
                preparedStatement = connection.prepareStatement(SQL_CHANGE_USER_PASSWORD);
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, user.getLogin());
                preparedStatement.executeUpdate();
                return findUserByLogin(connection, user.getLogin());
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    private User createUserFromQueryResult(ResultSet resultSet) throws DaoException {
        try {
            return new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    Role.findRoleByName(resultSet.getString(5)),
                    resultSet.getByte(6));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private int findUserRoleIdByName(String name) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        ProxyConnection connection = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ROLE_ID_BY_NAME);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getByte(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_CHANGE_STATUS_TO_INACTIVE);
            preparedStatement.setInt(1, user.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }
}





