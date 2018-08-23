package test.java.by.epam.tattoo.connection;

import main.java.by.epam.tattoo.connection.ConnectionPool;
import main.java.by.epam.tattoo.connection.ConnectionPoolException;
import main.java.by.epam.tattoo.connection.ProxyConnection;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;


public class ConnectionPoolTest {
    private static ConnectionPool pool = ConnectionPool.getInstance();
    private ProxyConnection connection;

    @Test
    public void initConnectionPool() {
        Assert.assertNotNull(pool);
    }

    @Test
    public void getConnection() {
        connection = pool.takeConnection();
        int freeConnectionsSize = pool.getFreeConnectionsSize();
        Assert.assertEquals(9, freeConnectionsSize);
    }

    @Test
    public void releaseConnection() throws ConnectionPoolException {
        pool.releaseConnection(connection);
        int freeConnectionsSize = pool.getFreeConnectionsSize();
        Assert.assertEquals(10, freeConnectionsSize);
    }

    @AfterClass
    public void closePool() throws ConnectionPoolException {
        pool.closeConnectionPool();
    }
}

