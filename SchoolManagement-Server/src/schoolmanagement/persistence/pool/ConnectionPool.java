/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.pool;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import schoolmanagement.config.Configuration;

/**
 *
 * @author ivano
 */
// Thread Safe Singleton Pattern
public class ConnectionPool {

    private static volatile ConnectionPool connectionPool = null;
    private static final int INITIAL_POOL_SIZE = 10;

    private String dbHost;
    private String dbPort;
    private String dbName;
    private String dbUsername;
    private String dbPassword;

    private final List<Connection> availableConnections;
    private final List<Connection> usedConnections;

    private ConnectionPool() throws IOException, SQLException {
        availableConnections = new ArrayList<>(INITIAL_POOL_SIZE);
        usedConnections = new ArrayList<>();
        readProperties();

        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            availableConnections.add(createConnection());
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection;
        if (availableConnections.isEmpty()) {
            availableConnections.add(createConnection());
        }

        connection = availableConnections.remove(availableConnections.size() - 1);

        return connection;
    }

    public void releaseConnection(Connection connection) throws SQLException {
        if (!connection.isClosed()) {
            availableConnections.add(connection);
        }
        usedConnections.remove(connection);
    }

    // optimized creation of thread safe singleton
    public static ConnectionPool getInstance() throws IOException, SQLException {
        ConnectionPool result = connectionPool;

        if (result == null) {
            synchronized (ConnectionPool.class) {
                result = connectionPool;
                if (result == null) {
                    result = connectionPool = new ConnectionPool();
                }
            }
        }
        return result;
    }

    private void readProperties() throws IOException {
        Properties properties = new Properties();

        try ( FileInputStream input = new FileInputStream(Configuration.CONFIG_FILE_PATH)) {
            properties.load(input);
            dbHost = properties.getProperty(Configuration.DB_HOST);
            dbPort = properties.getProperty(Configuration.DB_PORT);
            dbName = properties.getProperty(Configuration.DB_NAME);
            dbUsername = properties.getProperty(Configuration.DB_USERNAME);
            dbPassword = properties.getProperty(Configuration.DB_PASSWORD);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?serverTimezone=UTC", dbUsername, dbPassword);
    }

}
