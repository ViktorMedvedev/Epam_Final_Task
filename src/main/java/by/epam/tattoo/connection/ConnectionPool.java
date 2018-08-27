package main.java.by.epam.tattoo.connection;

import main.java.by.epam.tattoo.util.DataBaseInfo;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final int POOL_SIZE = 10;
    private static Logger logger = LogManager.getLogger();
    private static ConnectionPool instance;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static String url;
    private static String user;
    private static String password;
    private LinkedBlockingQueue<ProxyConnection> freeConnections;
    private ArrayDeque<ProxyConnection> busyConnections;

    private ConnectionPool() {
        register();
        initDatabase();
        initPool();
    }

    public int getFreeConnectionsSize() {
        return freeConnections.size();
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }


    private void register() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            logger.fatal("Couldn't register driver" + e);
            throw new RuntimeException("Couldn't register driver", e);
        }
    }
    private void initDatabase() {
//        ResourceBundle bundle = ResourceBundle.getBundle("/resources/database/database");
//        url = bundle.getString("database.url");
//        user = bundle.getString("database.user");
//        password = bundle.getString("database.password");
        url = DataBaseInfo.URL;
        password=DataBaseInfo.PASSWORD;
        user=DataBaseInfo.USER;
    }

    private void initPool() {
        freeConnections = new LinkedBlockingQueue<>();
        busyConnections = new ArrayDeque<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                createConnection();
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            }
        }
        if (freeConnections.isEmpty()) {
            logger.fatal("Couldn't init connection connection");
            throw new RuntimeException("Couldn't init connection connection");
        }
        if (freeConnections.size() == POOL_SIZE) {
            logger.log(Level.INFO, "Successfully initialized connection pool");
        }

    }

    private void createConnection() throws ConnectionPoolException {
        try {
            ProxyConnection connection = new ProxyConnection(DriverManager.getConnection(url, user, password));
            freeConnections.add(connection);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Couldn't create connection", e);
        }
    }

    public ProxyConnection takeConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            busyConnections.add(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
        }
        return connection;
    }


    public void releaseConnection(ProxyConnection connection) throws ConnectionPoolException {
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
            busyConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Couldn't release connection", e);
        }
    }

    public void closeConnectionPool() throws ConnectionPoolException {
        ProxyConnection connection;
        int currentPoolSize = freeConnections.size() + busyConnections.size();
        for (int i = 0; i < currentPoolSize; i++) {
            try {
                connection = freeConnections.take();
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                connection.closeConnection();
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, e);
            } catch (SQLException e) {
                throw new ConnectionPoolException("Couldn't close connection", e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.drivers().forEach(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Couldn't deregister driver", e);
            }
        });
    }
}

