package main.java.by.epam.tattoo.dao.order;

import main.java.by.epam.tattoo.connection.ConnectionPool;
import main.java.by.epam.tattoo.connection.ConnectionPoolException;
import main.java.by.epam.tattoo.connection.ProxyConnection;
import main.java.by.epam.tattoo.dao.AbstractDao;
import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.entity.order.Order;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDao implements AbstractDao<Order> {
    private static Logger logger = LogManager.getLogger();
    private final ConnectionPool pool;
    private static final String SQL_REGISTER_ORDER =
            "INSERT INTO orders (user_id, tattoo_id, totalprice) VALUES (?,?,?)";
    private static final String SQL_DELETE_ORDER =
            "DELETE FROM orders WHERE order_id = ?";
    private static final String SQL_FIND_LAST_ADDED_ORDER =
            "SELECT order_id, user_id, tattoo_id, totalprice, id_status FROM orders ORDER BY order_id DESC LIMIT 1";
    private static final String SQL_FIND_ORDER_STATUS_ID_BY_NAME =
            "SELECT id_status FROM order_status WHERE status_name = ?";
    private static final String SQL_FIND_ORDER_STATUS_NAME_BY_ID =
            "SELECT status_name FROM order_status WHERE id_status = ?";
    private static final String SQL_FIND_ORDER_BY_TATTOO_AND_USER_ID =
            "SELECT order_id, user_id, tattoo_id, totalprice, id_status FROM orders WHERE user_id = ? AND tattoo_id = ?";
    private static final String SQL_SELECT_ALL_ORDERS =
            "SELECT order_id, user_id, tattoo_id, totalprice, id_status FROM orders";
    private static final String SQL_SELECT_ALL_USER_ORDERS =
            "SELECT order_id, user_id, tattoo_id, totalprice, id_status FROM orders WHERE user_id = ?";

    public OrderDao() {
        pool = ConnectionPool.getInstance();
    }

    @Override
    public Order register(Order order) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            int userId = order.getUserId();
            int tattooId = order.getTattooId();
            if (orderExists(connection, userId, tattooId)) {
                return null;
            }
            BigDecimal totalPrice = order.getTotalPrice();
            preparedStatement = connection.prepareStatement(SQL_REGISTER_ORDER);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, tattooId);
            preparedStatement.setBigDecimal(3, totalPrice);
            preparedStatement.executeUpdate();
            return findLastAddedOrder(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Failed to add order", e);
        } finally {
            try {
                pool.releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Error: ", e);
            }
        }
    }

    public ArrayList<Order> findAllOrders() throws DaoException {
        ArrayList<Order> orders = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_ORDERS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(createOrderFromQueryResult(connection, resultSet));
            }
            return orders;
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

    public ArrayList<Order> findUserOrders(int id) throws DaoException {
        ArrayList<Order> orders = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USER_ORDERS);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(createOrderFromQueryResult(connection, resultSet));
            }
            return orders;
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

    private boolean orderExists(ProxyConnection connection, int userId, int tattooId) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_ORDER_BY_TATTOO_AND_USER_ID);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, tattooId);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Order findLastAddedOrder(ProxyConnection connection) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_LAST_ADDED_ORDER);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createOrderFromQueryResult(connection, resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("Failed", e);
        }
    }

    private String findOrderStatusNameById(ProxyConnection connection, byte id) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_ORDER_STATUS_NAME_BY_ID);
            preparedStatement.setByte(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed", e);
        }
    }

    private int findOrderStatusIdByName(ProxyConnection connection, String name) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_ORDER_STATUS_ID_BY_NAME);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed", e);
        }
    }

    private Order createOrderFromQueryResult(ProxyConnection connection, ResultSet resultSet) throws DaoException {
        try {
            return new Order(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getBigDecimal(4),
                    findOrderStatusNameById(connection, resultSet.getByte(5)));
        } catch (SQLException e) {
            throw new DaoException("Failed: ", e);
        }
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER);
            preparedStatement.setInt(1, order.getOrderId());
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
