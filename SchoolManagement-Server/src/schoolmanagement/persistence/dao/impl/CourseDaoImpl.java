/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.persistence.dao.CourseDao;
import schoolmanagement.persistence.mapper.MapperCourseRS;

/**
 *
 * @author ivano
 */
public class CourseDaoImpl implements CourseDao {

    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Course> getAllCourses() throws SQLException {
        final String sqlQuery = "SELECT c.id AS course_id,c.name AS course_name,c.start_date,c.end_date,c.group_capacity,l.id AS language_id,l.name AS language_name,l.level  FROM course c\n"
                + "INNER JOIN `language` l\n"
                + "ON c.language_id = l.id;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet rs = statement.executeQuery();
            return MapperCourseRS.mapCourseWithLanguage(rs);
        }
    }

}
