/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseGroup;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.commonlib.model.Tutor;
import schoolmanagement.persistence.dao.CourseDao;
import schoolmanagement.persistence.mapper.MapperCourseGroupRS;
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

    @Override
    public boolean updateCourse(Course course) throws SQLException {
        final String sqlQuery = "UPDATE Course SET name=?, start_date=?, end_date=?, group_capacity = ?, language_id = ?"
                + " WHERE id = ?";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, course.getName());
            statement.setDate(2, Date.valueOf(course.getStartDate()));
            statement.setDate(3, Date.valueOf(course.getEndDate()));
            statement.setInt(4, course.getGroupCapacity());
            statement.setLong(5, course.getLanguage().getId());
            statement.setLong(6, course.getId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean checkIfCourseGroupsExist(Course course) throws SQLException {
        final String sqlQuery = "SELECT * FROM course_group WHERE course_id = ?;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, course.getId());
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }
    }

    @Override
    public void deleteCourseEnrollments(Course course) throws SQLException {
        final String sqlQuery = "DELETE FROM course_enrollment WHERE course_id = ?";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, course.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteCourse(Course course) throws SQLException {
        final String sqlQuery = "DELETE FROM course WHERE id = ?";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, course.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public Long saveCourse(Course course) throws SQLException {
        final String sqlQuery = "INSERT INTO Course(name,start_date,end_date,group_capacity,language_id) VALUES(?,?,?,?,?)";
        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, course.getName());
            statement.setDate(2, Date.valueOf(course.getStartDate()));
            statement.setDate(3, Date.valueOf(course.getEndDate()));
            statement.setInt(4, course.getGroupCapacity());
            statement.setLong(5, course.getLanguage().getId());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            long key = rs.next() ? rs.getLong(1) : 0;
            return key;
        }
    }

    @Override
    public List<CourseGroup> getGroupsOfCourse(Course temp) throws SQLException {
        final String sqlQuery = "SELECT cg.id AS course_group_id,cg.course_id AS course_id,cg.name AS group_name, cg.number_of_students AS num_of_students FROM course_group cg\n"
                + "WHERE cg.course_id = ?;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, temp.getId());
            ResultSet rs = statement.executeQuery();
            List<CourseGroup> groups = MapperCourseGroupRS.mapPlainCourseGroups(temp, rs);

            for (CourseGroup group : groups) {
                populateGroupWithCurrentStudents(group);
                populateGroupWithCurrentTutors(group);
            }
            return groups;
        }

    }

    @Override
    public List<Student> getStudentsOfCourse(Course temp) throws SQLException {
        final String sqlQuery = "SELECT * FROM course_enrollment ce\n"
                + "	INNER JOIN student s\n"
                + "	ON ce.student_id = s.user_id\n"
                + "	INNER JOIN `user` u\n"
                + "	ON s.user_id = u.id\n"
                + "	WHERE ce.course_id = ?;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, temp.getId());
            ResultSet rs = statement.executeQuery();
            return MapperCourseRS.mapStudentsOfCourse(rs);
        }
    }

    @Override
    public Long saveCourseGroup(CourseGroup courseGroup) throws SQLException {
        final String sqlQuery = "INSERT INTO course_group(course_id,name,number_of_students) VALUES(?,?,?)";
        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, courseGroup.getCourse().getId());
            statement.setString(2, courseGroup.getName());
            statement.setInt(3, courseGroup.getNumOfStudents());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            long key = rs.next() ? rs.getLong(1) : 0;
            return key;
        }
    }

    @Override
    public boolean updateCourseGroupData(CourseGroup courseGroup) throws SQLException {
        final String sqlQuery = "UPDATE course_group SET name = ?, number_of_students = ? WHERE id = ?";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, courseGroup.getName());
            statement.setInt(2, courseGroup.getNumOfStudents());
            statement.setLong(3, courseGroup.getId());
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public void deleteTutorsFromGroup(CourseGroup courseGroup) throws SQLException {
        final String sqlQuery = "DELETE FROM tutor_assignment WHERE course_id = ? AND course_group_id = ?";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, courseGroup.getCourse().getId());
            statement.setLong(2, courseGroup.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteStudentsFromGroup(CourseGroup courseGroup) throws SQLException {
        final String sqlQuery = "DELETE FROM group_enrollment WHERE course_id = ? AND course_group_id = ?";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, courseGroup.getCourse().getId());
            statement.setLong(2, courseGroup.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void addStudentsInGroup(CourseGroup courseGroup) throws SQLException {
        final String sqlQuery = "INSERT INTO group_enrollment (student_id,course_id,course_group_id) VALUES (?,?,?)";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(2, courseGroup.getCourse().getId());
            statement.setLong(3, courseGroup.getId());
            for (Student temp : courseGroup.getStudents()) {
                System.out.println(temp);
                statement.setLong(1, temp.getId());
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void addTutorsInGroup(CourseGroup courseGroup) throws SQLException {
        final String sqlQuery = "INSERT INTO tutor_assignment (tutor_id,course_id,course_group_id) VALUES (?,?,?)";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(2, courseGroup.getCourse().getId());
            statement.setLong(3, courseGroup.getId());
            for (Tutor temp : courseGroup.getTutors()) {
                statement.setLong(1, temp.getId());
                statement.executeUpdate();
            }
        }
    }

    private void populateGroupWithCurrentStudents(CourseGroup group) throws SQLException {
        final String sqlQuery = "SELECT cg.id AS course_group_id,cg.course_id AS course_id,cg.name AS group_name, cg.number_of_students AS num_of_students,ge.student_id AS student_id,\n"
                + "s.first_name,s.last_name,s.birthdate,s.creation_date,u.username,u.password  FROM course_group cg\n"
                + "LEFT JOIN group_enrollment ge\n"
                + "ON (cg.id = ge.course_group_id AND cg.course_id = ge.course_id)\n"
                + "LEFT JOIN student s\n"
                + "ON ge.student_id = s.user_id\n"
                + "LEFT JOIN USER u\n"
                + "ON s.user_id = u.id\n"
                + "WHERE cg.course_id = ? AND course_group_id = ?;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, group.getCourse().getId());
            statement.setLong(2, group.getId());

            ResultSet rs = statement.executeQuery();
            MapperCourseGroupRS.mapStudentsInCourseGroup(group, rs);
        }
    }

    private void populateGroupWithCurrentTutors(CourseGroup group) throws SQLException {
        final String sqlQuery = "SELECT cg.id AS course_group_id,cg.course_id AS course_id,cg.name AS group_name, cg.number_of_students AS num_of_students,t.id AS tutor_id,t.first_name,t.last_name FROM course_group cg\n"
                + "	LEFT JOIN tutor_assignment ta\n"
                + "	ON (cg.id = ta.course_group_id AND cg.course_id = ta.course_id)\n"
                + "	LEFT JOIN tutor t\n"
                + "	ON ta.tutor_id = t.id\n"
                + "	WHERE cg.course_id = ? AND course_group_id = ?;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, group.getCourse().getId());
            statement.setLong(2, group.getId());

            ResultSet rs = statement.executeQuery();
            MapperCourseGroupRS.mapTutorsInCourseGroup(group, rs);
        }
    }

}
