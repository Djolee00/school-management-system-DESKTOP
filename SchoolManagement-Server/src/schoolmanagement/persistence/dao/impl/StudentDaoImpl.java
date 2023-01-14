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
import java.util.ArrayList;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseEnrollment;
import schoolmanagement.commonlib.model.CourseGroup;
import schoolmanagement.commonlib.model.Language;
import schoolmanagement.commonlib.model.Tutor;
import schoolmanagement.commonlib.model.User;
import schoolmanagement.commonlib.model.enums.Level;

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
        final String sqlQuery = "SELECT ce.student_id,ce.course_id,enrollment_date,c.`name` AS course_name,start_date,end_date,group_capacity,language_id, l.name AS `language_name`, l.level, ge.course_group_id,cg.name AS group_name,number_of_students,\n"
                + "ta.tutor_id,t.first_name AS tutor_first_name,t.last_name AS tutor_last_name\n"
                + "FROM course_enrollment ce \n"
                + "INNER JOIN course c ON ce.course_id = c.id \n"
                + "INNER JOIN `language` l ON c.language_id = l.id\n"
                + "LEFT JOIN group_enrollment ge ON ge.student_id = ce.student_id AND ge.course_id = c.id\n"
                + "LEFT JOIN course_group cg ON ge.course_group_id = cg.id AND ge.course_id = ce.course_id\n"
                + "LEFT JOIN tutor_assignment ta ON ce.course_id = ta.course_id AND ge.course_group_id = ta.course_group_id\n"
                + "LEFT JOIN tutor t ON t.id = ta.tutor_id\n"
                + "WHERE ce.student_id = ?;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            return mapResultSetToCourseEnrollments(rs);
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

            return mapResultSetToCourses(rs);
        }
    }

    private List<CourseEnrollment> mapResultSetToCourseEnrollments(ResultSet rs) throws SQLException {
        List<CourseEnrollment> courseEnrollments = new ArrayList<>();

        while (rs.next()) {
            Language language = makeLanguageFromRs(rs);
            Course course = makeCourseFromRs(rs);
            CourseGroup group = makeCourseGroupFromRs(rs);
            List<CourseGroup> courseGroups = new ArrayList<>();
            courseGroups.add(group);

            course.setLanguage(language);
            course.setCourseGroups(courseGroups);

            CourseEnrollment courseEnrollment = new CourseEnrollment();
            courseEnrollment.setCourse(course);
            courseEnrollment.setEnrollmentDate(rs.getDate("enrollment_date").toLocalDate());

            courseEnrollments.add(courseEnrollment);
        }

        return courseEnrollments;
    }

    private List<Course> mapResultSetToCourses(ResultSet rs) throws SQLException {
        List<Course> courses = new ArrayList<>();

        while (rs.next()) {
            Language language = makeLanguageFromRs(rs);
            Course course = makeCourseFromRs(rs);
            course.setLanguage(language);
            
            courses.add(course);
        }

        return courses;
    }

    private Course makeCourseFromRs(ResultSet rs) throws SQLException {
        Course temp = new Course();
        temp.setId(rs.getLong("course_id"));
        temp.setName(rs.getString("course_name"));
        temp.setStartDate(rs.getDate("start_date").toLocalDate());
        temp.setEndDate(rs.getDate("end_date").toLocalDate());
        temp.setGroupCapacity(rs.getInt("group_capacity"));

        return temp;
    }

    private Language makeLanguageFromRs(ResultSet rs) throws SQLException {
        Language temp = new Language();
        temp.setId(rs.getLong("language_id"));
        temp.setName(rs.getString("language_name"));
        temp.setLevel(Level.valueOf(rs.getString("level")));

        return temp;
    }

    private CourseGroup makeCourseGroupFromRs(ResultSet rs) throws SQLException {
        if (rs.getString("group_name") == null) {
            return null;
        }

        CourseGroup courseGroup = new CourseGroup();
        courseGroup.setId(rs.getLong("course_group_id"));
        courseGroup.setName(rs.getString("group_name"));
        courseGroup.setNumOfStudents(rs.getInt("number_of_students"));

        Tutor tutorOfGroup = makeTutorOfGroupFromRs(rs);
        courseGroup.setTutor(tutorOfGroup);

        return courseGroup;
    }

    private Tutor makeTutorOfGroupFromRs(ResultSet rs) throws SQLException {
        Tutor temp = new Tutor();
        temp.setId(rs.getLong("tutor_id"));
        temp.setFirstName(rs.getString("tutor_first_name"));
        temp.setLastName(rs.getString("tutor_last_name"));

        return temp;
    }

}
