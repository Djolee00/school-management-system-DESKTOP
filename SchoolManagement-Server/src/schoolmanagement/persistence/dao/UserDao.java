/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.persistence.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import schoolmanagement.commonlib.model.User;

/**
 *
 * @author ivano
 */
public interface UserDao {

    // needs to return generated id
    long saveUser(User user) throws SQLException, IOException;

    public boolean isUsernameUnique(String username) throws SQLException;

    void setConnection(Connection connection);

}
