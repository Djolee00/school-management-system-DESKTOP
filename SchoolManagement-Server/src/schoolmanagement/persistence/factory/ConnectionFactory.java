/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import schoolmanagement.config.Configuration;

/**
 *
 * @author ivano
 */

// Thread Safe Singleton Pattern
public class ConnectionFactory {

    private static volatile ConnectionFactory connectionFactory = null;

    private String dbHost;
    private String dbPort;
    private String dbName;
    private String dbUsername;
    private String dbPassword;

    private ConnectionFactory() throws IOException {
        readProperties();
    }

    public Connection getConnection() throws SQLException {
        Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName, dbUsername, dbPassword);
        return conn;
    }

    // optimized creation of thread safe singleton
    public static ConnectionFactory getInstance() throws IOException {
        ConnectionFactory result = connectionFactory;
        
        if (result == null) {
            synchronized (ConnectionFactory.class) {
                result = connectionFactory;
                if(result == null)
                    result = connectionFactory = new ConnectionFactory();
            }
        }
        return result;
    }

    private void readProperties() throws IOException {
        Properties properties = new Properties();
        
        try (FileInputStream input = new FileInputStream(Configuration.CONFIG_FILE_PATH)) {
            properties.load(input);
            dbHost = properties.getProperty(Configuration.DB_HOST);
            dbPort = properties.getProperty(Configuration.DB_PORT);
            dbName = properties.getProperty(Configuration.DB_NAME);
            dbUsername = properties.getProperty(Configuration.DB_USERNAME);
            dbPassword = properties.getProperty(Configuration.DB_PASSWORD);
        }
    }


}
