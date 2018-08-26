package main.java.by.epam.tattoo.dao.impl;

import java.sql.Blob;

import main.java.by.epam.tattoo.connection.ConnectionPool;
import main.java.by.epam.tattoo.connection.ConnectionPoolException;
import main.java.by.epam.tattoo.connection.ProxyConnection;
import main.java.by.epam.tattoo.dao.AbstractDao;
import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.entity.Tattoo;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TattooDaoImpl implements AbstractDao<Tattoo> {
    private static Logger logger = LogManager.getLogger();
    private final ConnectionPool pool;
    private static final String SQL_ADD_TATTOO =
            "INSERT INTO tattoo (style_id, size_id, price, image) VALUES (?,?,?,?)";
    private static final String SQL_ADD_TATTOO_RATE =
            "INSERT INTO users_rated_tattoo (user_id, tattoo_id, rate) VALUES (?,?,?)";
    private static final String SQL_FIND_RATE =
            "SELECT rate FROM users_rated_tattoo where user_id = ? AND tattoo_id = ?";

    private static final String SQL_FIND_RATES_SUM =
            "SELECT SUM(rate) FROM users_rated_tattoo WHERE tattoo_id = ?";
    private static final String SQL_FIND_RATES_QUANTITY =
            "SELECT COUNT(rate) FROM users_rated_tattoo WHERE tattoo_id = ?";
    private static final String SQL_SELECT_ALL_TATTOOS =
            "SELECT tattoo_id, style_name, size_name, price, image, rating \n" +
                    "FROM tattoo \n" +
                    "JOIN styles ON tattoo.style_id = styles.style_id \n" +
                    "JOIN sizes ON tattoo.size_id = sizes.size_id ORDER BY tattoo_id DESC";

    private static final String SQL_FIND_TATTOO_STYLE_ID_BY_NAME =
            "SELECT style_id FROM styles WHERE style_name = ?";
    private static final String SQL_FIND_TATTOO_SIZE_ID_BY_NAME =
            "SELECT size_id FROM sizes WHERE size_name = ?";

    private static final String SQL_FIND_TATTOO_BY_ID =
            "select tattoo_id, style_name, size_name, price, image, rating \n" +
                    "FROM tattoo \n" +
                    "JOIN styles on tattoo.style_id = styles.style_id \n" +
                    "JOIN sizes on tattoo.size_id = sizes.size_id \n" +
                    "WHERE tattoo_id = ?";

    private static final String SQL_FIND_TATTOO_IMAGE_BY_ID =
            "SELECT image FROM tattoo WHERE tattoo_id = ?";
    private static final String SQL_FIND_TATTOO_BY_SIZE =
            "select tattoo_id, style_name, size_name, price, image, rating \n" +
                    "FROM tattoo \n" +
                    "JOIN styles ON tattoo.style_id = styles.style_id \n" +
                    "JOIN sizes ON tattoo.size_id = sizes.size_id \n" +
                    "WHERE sizes.size_name = ?";
    private static final String SQL_FIND_TATTOO_BY_STYLE =
            "select tattoo_id, style_name, size_name, price, image, rating \n" +
                    "FROM tattoo \n" +
                    "JOIN styles ON tattoo.style_id = styles.style_id \n" +
                    "JOIN sizes ON tattoo.size_id = sizes.size_id \n" +
                    "WHERE styles.style_name = ?";
    private static final String SQL_FIND_TATTOO_BY_STYLE_AND_SIZE =
            "select tattoo_id, style_name, size_name, price, image, rating \n" +
                    "FROM tattoo \n" +
                    "JOIN styles ON tattoo.style_id = styles.style_id \n" +
                    "JOIN sizes ON tattoo.size_id = sizes.size_id \n" +
                    "WHERE styles.style_name = ? and sizes.size_name = ?";

    private static final String SQL_FIND_LAST_ADDED_TATTOO =
            "SELECT tattoo_id, style_name, size_name, price, image, rating \n" +
                    "FROM tattoo \n" +
                    "JOIN styles ON tattoo.style_id = styles.style_id \n" +
                    "JOIN sizes ON tattoo.size_id = sizes.size_id ORDER BY tattoo_id DESC LIMIT 1";

    private static final String SQL_DELETE_TATTOO =
            "DELETE FROM tattoo WHERE tattoo_id = ?";
    private static final String SQL_UPDATE_TATTOO_RATING =
            "UPDATE tattoo SET rating = ? WHERE tattoo_id = ?";

    public TattooDaoImpl() {
        pool = ConnectionPool.getInstance();
    }

    @Override
    public Tattoo register(Tattoo tattoo) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            String style = tattoo.getStyle();
            String size = tattoo.getSize();
            BigDecimal price = tattoo.getPrice();
            InputStream stream = new ByteArrayInputStream(tattoo.getImageBytes());
            preparedStatement = connection.prepareStatement(SQL_ADD_TATTOO);
            preparedStatement.setInt(1, findTattooStyleIdByName(style));
            preparedStatement.setInt(2, findTattooSizeIdByName(size));
            preparedStatement.setBigDecimal(3, price);
            preparedStatement.setBlob(4, stream);
            preparedStatement.executeUpdate();
            return findLastAddedTattoo();
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

    public ArrayList<Tattoo> findAllTattoos() throws DaoException {
        ArrayList<Tattoo> tattoos = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_TATTOOS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tattoos.add(createTattooFromQueryResult(resultSet));
            }
            return tattoos;
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

    public ArrayList<Tattoo> findTattoosByStyle(String style) throws DaoException {
        return getTattoosByParameter(style, SQL_FIND_TATTOO_BY_STYLE);
    }

    public ArrayList<Tattoo> findTattoosBySize(String size) throws DaoException {
        return getTattoosByParameter(size, SQL_FIND_TATTOO_BY_SIZE);
    }

    private ArrayList<Tattoo> getTattoosByParameter(String size, String sqlFindTattooBySize) throws DaoException {
        ArrayList<Tattoo> tattoos = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(sqlFindTattooBySize);
            preparedStatement.setString(1, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tattoos.add(createTattooFromQueryResult(resultSet));
            }
            return tattoos;
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

    public ArrayList<Tattoo> findTattoosByStyleAndSize(String style, String size) throws DaoException {
        ArrayList<Tattoo> tattoos = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_TATTOO_BY_STYLE_AND_SIZE);
            preparedStatement.setString(1, style);
            preparedStatement.setString(2, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tattoos.add(createTattooFromQueryResult(resultSet));
            }
            return tattoos;
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

    public Tattoo findTattooById(int id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_TATTOO_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createTattooFromQueryResult(resultSet);
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

    public byte[] findTattooImageById(int id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_TATTOO_IMAGE_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Blob blob = resultSet.getBlob("image");
                return blob.getBytes(1, (int) blob.length());
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

    private int findTattooStyleIdByName(String name) throws DaoException {
        return findTattooParameterIdByName(name, SQL_FIND_TATTOO_STYLE_ID_BY_NAME);
    }

    private int findTattooSizeIdByName(String name) throws DaoException {
        return findTattooParameterIdByName(name, SQL_FIND_TATTOO_SIZE_ID_BY_NAME);
    }

    private int findTattooParameterIdByName(String name, String sqlFindTattooSizeIdByName) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        ProxyConnection connection = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(sqlFindTattooSizeIdByName);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            closeStatement(preparedStatement);
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    public boolean addRate(int userId, int tattooId, int rate) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            if (hasAlreadyRated(userId, tattooId)) {
                return false;
            }
            preparedStatement = connection.prepareStatement(SQL_ADD_TATTOO_RATE);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, tattooId);
            preparedStatement.setInt(3, rate);
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

    public boolean hasAlreadyRated(int userId, int tattooId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_RATE);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, tattooId);
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

    public int findRatesSum(int tattooId) throws DaoException {
        return findRatesParameter(tattooId, SQL_FIND_RATES_SUM);
    }

    public int findRatesQuantity(int tattooId) throws DaoException {
        return findRatesParameter(tattooId, SQL_FIND_RATES_QUANTITY);
    }

    private int findRatesParameter(int tattooId, String sqlFindRatesQuantity) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(sqlFindRatesQuantity);
            preparedStatement.setInt(1, tattooId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
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

    private Tattoo createTattooFromQueryResult(ResultSet resultSet) throws DaoException {
        try {
            return new Tattoo(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getBigDecimal(4),
                    resultSet.getBinaryStream(5).readAllBytes(),
                    resultSet.getBigDecimal(6));
        } catch (IOException | SQLException e) {
            throw new DaoException("Failed to add tattoo", e);
        }
    }

    private Tattoo findLastAddedTattoo() throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        ProxyConnection connection = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_LAST_ADDED_TATTOO);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createTattooFromQueryResult(resultSet);
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

    @Override
    public boolean delete(Tattoo tattoo) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_TATTOO);
            preparedStatement.setInt(1, tattoo.getId());
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

    public boolean updateRating(int tattooId, BigDecimal rating) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_TATTOO_RATING);
            preparedStatement.setBigDecimal(1, rating);
            preparedStatement.setInt(2, tattooId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

}
