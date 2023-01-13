/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.service.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import schoolmanagement.commonlib.model.User;
import schoolmanagement.persistence.dao.AdminDao;
import schoolmanagement.persistence.dao.StudentDao;
import schoolmanagement.persistence.dao.UserDao;
import schoolmanagement.persistence.pool.ConnectionPool;
import schoolmanagement.service.UserService;

/**
 *
 * @author ivano
 */
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final StudentDao studentDao;
    private final AdminDao adminDao;

    public UserServiceImpl(UserDao userDao, StudentDao studentDao, AdminDao adminDao) {
        this.userDao = userDao;
        this.studentDao = studentDao;
        this.adminDao = adminDao;
    }

    @Override
    public User login(String username, String password) throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            userDao.setConnection(connection);
            studentDao.setConnection(connection);
            adminDao.setConnection(connection);
            
            connection.setAutoCommit(false);

            User user = userDao.getUserByUsernameAndPassword(username, password);
            User temp = null;

            if (user != null) {
                temp = studentDao.getStudentByUser(user);
                
                if (temp == null) {
                    temp = adminDao.getAdminByUser(user);
                }
            }

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return temp;

        } catch (IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }

    }

}
