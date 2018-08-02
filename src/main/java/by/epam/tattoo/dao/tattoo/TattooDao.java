package main.java.by.epam.tattoo.dao.tattoo;

import java.sql.Blob;

import main.java.by.epam.tattoo.connection.ConnectionPool;
import main.java.by.epam.tattoo.connection.ConnectionPoolException;
import main.java.by.epam.tattoo.connection.ProxyConnection;
import main.java.by.epam.tattoo.dao.AbstractDao;
import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.entity.tattoo.Tattoo;
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
import java.util.Arrays;

public class TattooDao implements AbstractDao<Tattoo> {
    private static Logger logger = LogManager.getLogger();
    private final ConnectionPool pool;
    private static final String SQL_ADD_TATTOO =
            "INSERT INTO tattoo (name, style_id, size_id, price, image) VALUES (?,?,?,?,?)";
    private static final String SQL_SELECT_ALL_TATTOOS =
            "SELECT tattoo_id, name, style_id, size_id, price, image, rating FROM tattoo";

    private static final String SQL_FIND_TATTOO_STYLE_BY_ID =
            "SELECT style_id FROM styles WHERE style_name = ?";
    private static final String SQL_FIND_TATTOO_SIZE_BY_ID =
            "SELECT size_id FROM sizes WHERE size_name = ?";

    private static final String SQL_FIND_TATTOO_STYLE_ID_BY_NAME =
            "SELECT style_name FROM styles WHERE style_id = ?";
    private static final String SQL_FIND_TATTOO_SIZE_ID_BY_NAME =
            "SELECT size_name FROM sizes WHERE size_id = ?";

    private static final String SQL_FIND_TATTOO_BY_ID =
            "SELECT tattoo_id, name, style_id, size_id, price, image, rating FROM tattoo WHERE tattoo_id = ?";

    private static final String SQL_FIND_TATTOO_IMAGE_BY_ID =
            "SELECT image FROM tattoo WHERE tattoo_id = ?";

    private static final String SQL_FIND_LAST_ADDED_TATTOO =
            "SELECT tattoo_id, name, style_id, size_id, price, image, rating FROM tattoo ORDER BY tattoo_id DESC LIMIT 1";

    private static final String SQL_DELETE_TATTOO =
            "DELETE FROM tattoo WHERE tattoo_id = ?";

    public TattooDao() {
        pool = ConnectionPool.getInstance();
    }

    @Override
    public Tattoo register(Tattoo tattoo) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            String name = tattoo.getName();
            String style = tattoo.getStyle();
            String size = tattoo.getSize();
            BigDecimal price = tattoo.getPrice();
            InputStream stream = new ByteArrayInputStream(tattoo.getImageBytes());
            preparedStatement = connection.prepareStatement(SQL_ADD_TATTOO);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, style);
            preparedStatement.setString(3, size);
            preparedStatement.setBigDecimal(4, price);
            preparedStatement.setBlob(5, stream);
            preparedStatement.executeUpdate();
            return findLastAddedTattoo(connection);

        } catch (SQLException e) {
            throw new DaoException("Failed to add tattoo", e);
        } finally {
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
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_TATTOOS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tattoos.add(createTattooFromQueryResult(connection, resultSet));
            }
            return tattoos;
        } catch (SQLException e) {
            throw new DaoException("Failed to add tattoo", e);
        } finally {
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    public Tattoo findTattooById(int id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_TATTOO_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createTattooFromQueryResult(connection, resultSet);
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

    public byte[] findTattooImageById(int id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
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
            throw new DaoException("Failed", e);
        } finally {
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    private String findTattooStyleById(ProxyConnection connection, int id) throws DaoException {
        return findTattooParameterNameById(connection, (byte) id, SQL_FIND_TATTOO_STYLE_ID_BY_NAME);
    }

    private String findTattooSizeById(ProxyConnection connection, int id) throws DaoException {
        return findTattooParameterNameById(connection, (byte) id, SQL_FIND_TATTOO_SIZE_ID_BY_NAME);
    }

    private String findTattooParameterNameById(ProxyConnection connection, byte id, String sqlFindTattooStyleIdByName) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(sqlFindTattooStyleIdByName);
            preparedStatement.setByte(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("Failed", e);
        }
    }

    private int findTattooStyleIdByName(ProxyConnection connection, String name) throws DaoException {
        return findTattooParameterIdByName(connection, name, SQL_FIND_TATTOO_STYLE_ID_BY_NAME);
    }

    private int findTattooSizeIdByName(ProxyConnection connection, String name) throws DaoException {
        return findTattooParameterIdByName(connection, name, SQL_FIND_TATTOO_SIZE_ID_BY_NAME);
    }

    private int findTattooParameterIdByName(ProxyConnection connection, String name, String sqlFindTattooSizeIdByName) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(sqlFindTattooSizeIdByName);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new DaoException("Failed", e);
        }
    }

    private Tattoo createTattooFromQueryResult(ProxyConnection connection, ResultSet resultSet) throws DaoException {
        try {
            return new Tattoo(resultSet.getInt(1),
                    resultSet.getString(2),
                    findTattooStyleById(connection, resultSet.getByte(3)),
                    findTattooSizeById(connection, resultSet.getByte(4)),
                    resultSet.getBigDecimal(5),
                    resultSet.getBinaryStream(6).readAllBytes(),
                    resultSet.getBigDecimal(7));
        } catch (IOException | SQLException e) {
            throw new DaoException("Failed to add tattoo", e);
        }
    }

    private Tattoo findLastAddedTattoo(ProxyConnection connection) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_LAST_ADDED_TATTOO);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createTattooFromQueryResult(connection, resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("Failed", e);
        }
    }

    @Override
    public boolean delete(Tattoo tattoo) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_TATTOO);
            preparedStatement.setInt(1, tattoo.getId());
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
