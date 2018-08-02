package main.java.by.epam.tattoo.dao.user;


import main.java.by.epam.tattoo.connection.ConnectionPool;
import main.java.by.epam.tattoo.connection.ConnectionPoolException;
import main.java.by.epam.tattoo.connection.ProxyConnection;
import main.java.by.epam.tattoo.dao.AbstractDao;
import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.entity.user.Role;
import main.java.by.epam.tattoo.entity.user.User;
import main.java.by.epam.tattoo.util.ConstantHeap;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements AbstractDao<User> {
    private final ConnectionPool pool;
    private static Logger logger = LogManager.getLogger();
    private static final String SQL_CHECK_USER_MATCHES =
            "SELECT login, password FROM users WHERE login =? AND password = SHA1(?)";

    private static final String SQL_CHECK_LOGIN_EXISTS =
            "SELECT login FROM users WHERE login =?";

    private static final String SQL_CHECK_EMAIL_EXISTS =
            "SELECT email FROM users WHERE email =?";

    private static final String SQL_INSERT_USER =
            "INSERT INTO users(email, login, password, role_id) VALUES (?, ?, SHA1(?), ?)";

    private static final String SQL_UPDATE_USER_DISCOUNT =
            "UPDATE users SET discount_pct = discount_pct + ? WHERE login = ?";

    private static final String SQL_CHANGE_USER_PASSWORD =
            "UPDATE users SET password = SHA1(?) WHERE login = ?";

    private static final String SQL_FIND_USER_BY_LOGIN =
            "SELECT user_id, email, login, password, role_id, discount_pct FROM users WHERE login =?";
    private static final String SQL_FIND_USER_BY_ID =
            "SELECT user_id, email, login, password, role_id, discount_pct FROM users WHERE user_id =?";

    private static final String SQL_DELETE_USER =
            "DELETE FROM users WHERE login =?";


    public UserDao() {
        pool = ConnectionPool.getInstance();
    }

    public User register(User user) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            String email = user.getEmail();
            String login = user.getLogin();
            String password = user.getPassword();
            byte role = (byte) user.getRole().getRoleNumber();
            if (emailExists(connection, email) || loginExists(connection, login)) {
                return null;
            }
            preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.setByte(4, role);
            preparedStatement.executeUpdate();
            return findUserByLogin(connection, login);
        } catch (SQLException e) {
            throw new DaoException("Failed to register user", e);
        } finally {
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);

            }
        }
    }

    private boolean loginExists(ProxyConnection connection, String email) throws DaoException {
        return checkParameterExist(connection, email, SQL_CHECK_LOGIN_EXISTS);
    }

    private boolean emailExists(ProxyConnection connection, String email) throws DaoException {
        return checkParameterExist(connection, email, SQL_CHECK_EMAIL_EXISTS);
    }

    private boolean checkParameterExist(ProxyConnection connection, String email, String sqlCheckLoginExists) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(sqlCheckLoginExists);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public User login(User user) throws DaoException {
        ProxyConnection connection = null;
        try {
            connection = pool.takeConnection();
            String login = user.getLogin();
            String password = user.getPassword();
            if (userMatches(connection, login, password)) {
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


    private boolean userMatches(ProxyConnection connection, String login, String password) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(SQL_CHECK_USER_MATCHES);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean updateDiscount(String login, byte pct) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            User user = findUserByLogin(connection, login);

            if (user != null && checkCorrectDiscountValue(user)) {
                preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_DISCOUNT);
                preparedStatement.setByte(1, pct);
                preparedStatement.setString(2, login);
                preparedStatement.executeUpdate();
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DaoException("Failed to login ", e);
        } finally {
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    private boolean checkCorrectDiscountValue(User user) {
        return user.getDiscountPct() < ConstantHeap.MAX_DISCOUNT_VALUE;
    }

    private User findUserByLogin(ProxyConnection connection, String login) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createUserFromQueryResult(connection, resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("Failed", e);
        } finally {
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    public User findUserById(int id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createUserFromQueryResult(connection, resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("Failed", e);
        }
    }

    public User changeUserPassword(User user, String newPassword) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            if (userMatches(connection, user.getLogin(), user.getPassword())) {
                preparedStatement = connection.prepareStatement(SQL_CHANGE_USER_PASSWORD);
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, user.getLogin());
                preparedStatement.executeUpdate();
                return findUserByLogin(connection, user.getLogin());
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("Failed ", e);
        } finally {
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    private User createUserFromQueryResult(ProxyConnection connection, ResultSet resultSet) throws DaoException {
        try {
            return new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    Role.getRoleByNumber(resultSet.getByte(5)),
                    resultSet.getByte(6));
        } catch (SQLException e) {
            throw new DaoException("Failed to add tattoo", e);
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Failed ", e);
        } finally {
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }
}





