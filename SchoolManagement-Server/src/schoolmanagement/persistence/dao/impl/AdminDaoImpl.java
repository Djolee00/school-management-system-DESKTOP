/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import schoolmanagement.commonlib.model.Administrator;
import schoolmanagement.commonlib.model.User;
import schoolmanagement.persistence.dao.AdminDao;

/**
 *
 * @author ivano
 */
public class AdminDaoImpl implements AdminDao {

    private Connection connection;

    @Override
    public User getAdminByUser(User user) throws SQLException {
        final String sqlQuery = "SELECT * FROM Administrator WHERE user_id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setLong(1, user.getId());

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Administrator(user.getId(),user.getUsername(), user.getPassword(), rs.getDate("employment_date").toLocalDate());
            } else {
                return null;
            }

        }
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
