package main.java.by.epam.tattoo.dao.offer;

import main.java.by.epam.tattoo.connection.ConnectionPool;
import main.java.by.epam.tattoo.connection.ConnectionPoolException;
import main.java.by.epam.tattoo.connection.ProxyConnection;
import main.java.by.epam.tattoo.dao.AbstractDao;
import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.entity.Offer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OfferDao implements AbstractDao<Offer> {
    private static Logger logger = LogManager.getLogger();
    private final ConnectionPool pool;
    private static final String SQL_ADD_OFFER =
            "INSERT INTO offers (users_user_id, image) VALUES (?,?)";
    private static final String SQL_FIND_LAST_ADDED_OFFER_BY_USER =
            "SELECT offers_id, users_user_id, image " +
                    "FROM offers " +
                    "WHERE users_user_id = ? " +
                    "ORDER BY offers_id " +
                    "DESC LIMIT 1";
    private static final String SQL_SELECT_ALL_OFFERS = "SELECT offers_id, users_user_id, image FROM offers";
    private static final String SQL_DELETE_ALL_USER_OFFERS =
            "DELETE FROM offers WHERE users_user_id = ?";
    private static final String SQL_DELETE_OFFER =
            "DELETE FROM offers WHERE offers_id = ?";
    private static final String SQL_FIND_TATTOO_IMAGE_BY_OFFER_ID =
            "SELECT image FROM offers WHERE offers_id = ?";
    private static final String SQL_FIND_OFFER_BY_ID =
            "SELECT offers_id, users_user_id, image FROM offers WHERE offers_id = ?";

    public OfferDao() {
        pool = ConnectionPool.getInstance();
    }

    @Override
    public Offer register(Offer offer) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            InputStream stream = new ByteArrayInputStream(offer.getImageBytes());
            preparedStatement = connection.prepareStatement(SQL_ADD_OFFER);
            preparedStatement.setInt(1, offer.getUserId());
            preparedStatement.setBlob(2, stream);
            preparedStatement.executeUpdate();
            return findLastAddedOfferByUser(offer.getUserId());
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

    public Offer findOfferById(int id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_OFFER_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createOfferFromQueryResult(resultSet);
            }
            return null;
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

    public byte[] findTattooImageById(int id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_TATTOO_IMAGE_BY_OFFER_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Blob blob = resultSet.getBlob(1);
                return blob.getBytes(1, (int) blob.length());
            }
            return null;
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

    public boolean deleteAllUserOffers(int userId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ALL_USER_OFFERS);
            preparedStatement.setInt(1, userId);
            return preparedStatement.executeUpdate() >= 0;
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

    public ArrayList<Offer> findAllOffers() throws DaoException {
        ArrayList<Offer> offers = new ArrayList<>();

        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ProxyConnection connection = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_OFFERS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                offers.add(createOfferFromQueryResult(resultSet));
            }
            return offers;
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

    private Offer findLastAddedOfferByUser(int id) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ProxyConnection connection = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_LAST_ADDED_OFFER_BY_USER);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createOfferFromQueryResult(resultSet);
            } else {
                return null;
            }
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

    private Offer createOfferFromQueryResult(ResultSet resultSet) throws DaoException {
        try {
            return new Offer(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getBinaryStream(3).readAllBytes());
        } catch (IOException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Offer offer) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_OFFER);
            preparedStatement.setInt(1, offer.getOfferId());
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

