/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.dao.impl;

import java.io.IOException;
import java.util.List;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.persistence.dao.StudentDao;
import java.sql.*;
import java.time.LocalDateTime;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseEnrollment;
import schoolmanagement.commonlib.model.User;
import schoolmanagement.persistence.mapper.MapperStudentRS;

/**
 *
 * @author ivano
 */
public class StudentDaoImpl implements StudentDao {

    private Connection connection;

    @Override
    public Student saveStudent(Student student) throws SQLException, IOException {
        final String sqlQuery = "INSERT INTO Student(user_id,first_name,last_name,birthdate,creation_date) VALUES(?,?,?,?,?)";
        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setLong(1, student.getId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setDate(4, Date.valueOf(student.getBirthdate()));
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            statement.executeUpdate();

            return student;
        }
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student getStudentByUser(User user) throws SQLException {
        final String sqlQuery = "SELECT * FROM Student WHERE user_id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, user.getId());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Student(user.getId(), user.getUsername(), user.getPassword(), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birthdate").toLocalDate(), rs.getDate("creation_date").toLocalDate());
            } else {
                return null;
            }
        }
    }

    @Override
    public List<CourseEnrollment> getStudentCourses(Long id) throws SQLException {
        final String sqlQuery = "SELECT ce.student_id,ce.course_id,enrollment_date,c.`name` AS course_name,start_date,end_date,group_capacity,language_id, l.name AS `language_name`, l.level, ge.course_group_id,cg.name AS group_name,number_of_students\n"
                + "FROM course_enrollment ce \n"
                + "INNER JOIN course c ON ce.course_id = c.id \n"
                + "INNER JOIN `language` l ON c.language_id = l.id\n"
                + "LEFT JOIN group_enrollment ge ON ge.student_id = ce.student_id AND ge.course_id = c.id\n"
                + "LEFT JOIN course_group cg ON ge.course_group_id = cg.id AND ge.course_id = ce.course_id\n"
                + "WHERE ce.student_id = ?;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            return MapperStudentRS.mapResultSetToCourseEnrollments(rs, connection);
        }
    }

    @Override
    public List<Course> getStudentUnselecteCourses(Long id) throws SQLException {
        final String sqlQuery = "SELECT c.id AS course_id, c.`name` AS course_name,c.start_date,c.end_date,c.group_capacity,c.language_id, l.`name` AS language_name,l.level FROM course c \n"
                + "INNER JOIN `language` l ON c.language_id = l.id\n"
                + "WHERE c.id NOT IN (SELECT course_id FROM course_enrollment WHERE student_id=?);";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            return MapperStudentRS.mapResultSetToCourses(rs);
        }
    }

    @Override
    public boolean saveStudentSelectedCourses(List<CourseEnrollment> selectedCourses) throws SQLException {
        final String sqlQuery = "INSERT INTO course_enrollment(student_id,course_id,enrollment_date) VALUES(?,?,?);";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            for (CourseEnrollment selectedCourse : selectedCourses) {
                statement.setLong(1, selectedCourse.getStudent().getId());
                statement.setLong(2, selectedCourse.getCourse().getId());
                statement.setDate(3, Date.valueOf(selectedCourse.getEnrollmentDate()));

                int affectedRows = statement.executeUpdate();
                if (affectedRows != 1) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        final String sqlQuery = "SELECT s.user_id, s.first_name,s.last_name, s.birthdate,s.creation_date,u.username,u.password\n"
                + "FROM student s\n"
                + "INNER JOIN `user` u\n"
                + "ON s.user_id = u.id;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet rs = statement.executeQuery();

            return MapperStudentRS.mapStudents(rs, connection);
        }
    }

    @Override
    public boolean updateStudent(Student student) throws SQLException {
        final String sqlQuery = "UPDATE Student SET first_name = ?, last_name = ?, birthdate = ? WHERE user_id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setDate(3, Date.valueOf(student.getBirthdate()));
            statement.setLong(4, student.getId());
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
            
        }
    }

}
