package main.java.by.epam.tattoo.controller.listener;
import main.java.by.epam.tattoo.connection.ConnectionPool;
import main.java.by.epam.tattoo.connection.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConnectionPoolListener implements ServletContextListener {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance();
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().closeConnectionPool();
        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL, "Couldn't close connection pool", e);
            throw new RuntimeException(e);
        }
    }
}