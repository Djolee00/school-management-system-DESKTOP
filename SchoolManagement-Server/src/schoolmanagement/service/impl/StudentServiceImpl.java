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
import schoolmanagement.validator.StudentValidator;
import validation.exception.ValidationException;
import validaton.rule.result.ResultInfo;
import java.sql.SQLException;
import schoolmanagement.persistence.dao.UserDao;
import schoolmanagement.persistence.pool.ConnectionPool;

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
    public synchronized Student save(Student student) throws ValidationException, IOException, SQLException {

        validateStudentData(student);

        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            userDao.setConnection(connection);
            studentDao.setConnection(connection);

            connection.setAutoCommit(false);

            validateStudentUsername(student.getUsername());

            long userId = userDao.saveUser(student);
            student.setId(userId);
            student = studentDao.saveStudent(student);

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return student;
        } catch (IOException | SQLException ex) {
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

    private void validateStudentData(Student student) throws ValidationException {
        ResultInfo result = new StudentValidator(student).validate();
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }

    }

    private void validateStudentUsername(String username) throws SQLException, ValidationException {
        boolean isUnique = userDao.isUsernameUnique(username);
        if (isUnique == false) {
            throw new ValidationException("Korisnicko ime vec postoji u sistemu!");
        }
    }

}
