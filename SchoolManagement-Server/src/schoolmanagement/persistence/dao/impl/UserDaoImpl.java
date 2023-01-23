/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.dao.impl;

import java.sql.Connection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import schoolmanagement.commonlib.model.User;
import schoolmanagement.persistence.dao.UserDao;

/**
 *
 * @author ivano
 */
public class UserDaoImpl implements UserDao {

    private Connection connection;

    @Override
    public long saveUser(User user) throws SQLException, IOException {
        final String sqlQuery = "INSERT INTO User(username,password) VALUES(?,?)";
        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            long key = rs.next() ? rs.getLong(1) : 0;
            return key;
        }
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isUsernameUnique(String username) throws SQLException {
        final String sqlQuery = "SELECT COUNT(*) FROM User WHERE username = ?";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            return (rs.next() && rs.getInt(1) == 0);

        }
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        final String sqlQuery = "SELECT * FROM User where username=? AND password=?";
        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        final String sqlQuery = "UPDATE User SET username = ?, password = ? WHERE id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }

}
