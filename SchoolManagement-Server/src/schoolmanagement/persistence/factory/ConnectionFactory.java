/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import schoolmanagement.config.Configuration;

/**
 *
 * @author ivano
 */
public class ConnectionFactory {

    private static ConnectionFactory connectionFactory = null;

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
        conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName,dbUsername,dbPassword);
        return conn;
    }

    public static ConnectionFactory getInstance() throws IOException {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }

    private void readProperties() throws  IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(Configuration.CONFIG_FILE_PATH));
        dbHost = properties.getProperty(Configuration.DB_HOST);
        dbPort = properties.getProperty(Configuration.DB_PORT);
        dbName = properties.getProperty(Configuration.DB_NAME);
        dbUsername = properties.getProperty(Configuration.DB_USERNAME);
        dbPassword = properties.getProperty(Configuration.DB_PASSWORD);
    }

}
