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
    public synchronized boolean updateCourseData(Course course) throws IOException, SQLException {
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

}
