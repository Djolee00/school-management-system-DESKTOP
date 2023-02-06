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
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseEnrollment;
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
    public synchronized Student saveStudent(Student student, StudentValidator validator) throws ValidationException, IOException, SQLException {
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
    public List<CourseEnrollment> getStudentCourses(Long id) throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        List<CourseEnrollment> courses;

        try {
            studentDao.setConnection(connection);

            connection.setAutoCommit(false);

            courses = studentDao.getStudentCourses(id);

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return courses;
        } catch (IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }
    }

    @Override
    public List<Course> getStudentUnselectedCourses(Long id) throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        List<Course> courses;

        try {
            studentDao.setConnection(connection);

            connection.setAutoCommit(false);

            courses = studentDao.getStudentUnselecteCourses(id);

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return courses;
        } catch (IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }

    }

    @Override
    public synchronized boolean enrollCourses(List<CourseEnrollment> selectedCourses) throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            studentDao.setConnection(connection);

            connection.setAutoCommit(false);

            boolean status = studentDao.saveStudentSelectedCourses(selectedCourses);

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return status;
        } catch (IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }
    }

    @Override
    public List<Student> getAllStudents() throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        List<Student> students;

        try {
            studentDao.setConnection(connection);

            connection.setAutoCommit(false);

            students = studentDao.getAllStudents();

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return students;
        } catch (IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }

    }

    @Override
    public synchronized boolean updateStudent(Student student, StudentValidator validator) throws ValidationException, IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            userDao.setConnection(connection);
            studentDao.setConnection(connection);

            connection.setAutoCommit(false);

            validator.validate(student, userDao);

            boolean statusUser = userDao.updateUser(student);
            boolean statusStudent = studentDao.updateStudent(student);

            if (statusStudent == false || statusUser == false) {
                connection.rollback();
                ConnectionPool.getInstance().releaseConnection(connection);
                return false;
            }

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);
            return true;
        } catch (ValidationException | IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }
    }

}
