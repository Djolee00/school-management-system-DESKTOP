/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.service.impl;

import java.sql.Connection;
import java.io.IOException;
import java.util.List;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.persistence.dao.StudentDao;
import schoolmanagement.service.StudentService;
import validation.exception.ValidationException;
import java.sql.SQLException;
import schoolmanagement.persistence.dao.UserDao;
import schoolmanagement.persistence.pool.ConnectionPool;
import schoolmanagement.validator.student.StudentValidator;

/**
 *
 * @author ivano
 */
public class StudentServiceImpl implements StudentService {

    private final UserDao userDao;
    private final StudentDao studentDao;

    public StudentServiceImpl(UserDao userDao, StudentDao studentDao) {
        this.studentDao = studentDao;
        this.userDao = userDao;
    }

    @Override
    public synchronized Student save(Student student,StudentValidator validator) throws ValidationException, IOException, SQLException {


        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            userDao.setConnection(connection);
            studentDao.setConnection(connection);

            connection.setAutoCommit(false);

            validator.validate(student, userDao);
                    
            long userId = userDao.saveUser(student);
            student.setId(userId);
            student = studentDao.saveStudent(student);

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return student;
        } catch (ValidationException | IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Student getById() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Student> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
