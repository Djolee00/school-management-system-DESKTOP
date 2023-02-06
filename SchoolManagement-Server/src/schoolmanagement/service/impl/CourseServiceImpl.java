/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.service.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseGroup;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.persistence.dao.CourseDao;
import schoolmanagement.persistence.pool.ConnectionPool;
import schoolmanagement.service.CourseService;

/**
 *
 * @author ivano
 */
public class CourseServiceImpl implements CourseService {

    private final CourseDao courseDao;

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public List<Course> getAllCourses() throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        List<Course> courses;

        try {
            courseDao.setConnection(connection);

            connection.setAutoCommit(false);

            courses = courseDao.getAllCourses();

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
    public synchronized boolean updateCourse(Course course) throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            courseDao.setConnection(connection);

            connection.setAutoCommit(false);

            boolean status = courseDao.updateCourse(course);
            if (status == false) {
                connection.rollback();
                ConnectionPool.getInstance().releaseConnection(connection);
                return false;
            }

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return true;
        } catch (IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }
    }

    @Override
    public synchronized boolean deleteCourse(Course course) throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            courseDao.setConnection(connection);

            connection.setAutoCommit(false);

            boolean hasCourseGroups = courseDao.checkIfCourseGroupsExist(course);
            if (hasCourseGroups == true) {
                connection.rollback();
                ConnectionPool.getInstance().releaseConnection(connection);
                return false;
            }

            courseDao.deleteCourseEnrollments(course);
            courseDao.deleteCourse(course);

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return true;
        } catch (IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }
    }

    @Override
    public synchronized Long saveCourse(Course course) throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        Long generatedId;

        try {
            courseDao.setConnection(connection);

            connection.setAutoCommit(false);

            generatedId = courseDao.saveCourse(course);

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return generatedId;
        } catch (IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }
    }

    @Override
    public List<CourseGroup> getGroupOfCourse(Course temp) throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        List<CourseGroup> courseGroups;

        try {
            courseDao.setConnection(connection);

            connection.setAutoCommit(false);

            courseGroups = courseDao.getGroupsOfCourse(temp);

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return courseGroups;
        } catch (IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }
    }

    @Override
    public List<Student> getCourseStudents(Course temp) throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        List<Student> students;

        try {
            courseDao.setConnection(connection);

            connection.setAutoCommit(false);

            students = courseDao.getStudentsOfCourse(temp);

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
    public synchronized Long saveCourseGroup(CourseGroup courseGroup) throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        Long generatedId;

        try {
            courseDao.setConnection(connection);

            connection.setAutoCommit(false);

            generatedId = courseDao.saveCourseGroup(courseGroup);

            courseGroup.setId(generatedId);
//            courseDao.deleteTutorsFromGroup(courseGroup);
//            courseDao.deleteStudentsFromGroup(courseGroup);
            courseDao.addStudentsInGroup(courseGroup);
            courseDao.addTutorsInGroup(courseGroup);

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);

            return generatedId;
        } catch (IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }
    }

    @Override
    public synchronized boolean updateCourseGroup(CourseGroup courseGroup) throws IOException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            courseDao.setConnection(connection);

            connection.setAutoCommit(false);

            boolean status = courseDao.updateCourseGroupData(courseGroup);
            if (status == false) {
                connection.rollback();
                ConnectionPool.getInstance().releaseConnection(connection);
                return false;
            }
            courseDao.deleteTutorsFromGroup(courseGroup);
            courseDao.deleteStudentsFromGroup(courseGroup);
            courseDao.addStudentsInGroup(courseGroup);
            courseDao.addTutorsInGroup(courseGroup);

            connection.commit();
            ConnectionPool.getInstance().releaseConnection(connection);
            return true;
        } catch (IOException | SQLException ex) {
            connection.rollback();
            ConnectionPool.getInstance().releaseConnection(connection);
            throw ex;
        }
    }

}
