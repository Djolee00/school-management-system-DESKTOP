/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseEnrollment;
import schoolmanagement.commonlib.model.CourseGroup;
import schoolmanagement.commonlib.model.Language;
import schoolmanagement.commonlib.model.Tutor;
import schoolmanagement.commonlib.model.enums.Level;
import java.sql.Connection;
import schoolmanagement.commonlib.model.Student;

/**
 * TODO: work with connections should be in StudentDAO
 *
 * @author ivano
 */
public class MapperStudentRS {

    public static List<CourseEnrollment> mapResultSetToCourseEnrollments(ResultSet rs, Connection conn) throws SQLException {
        List<CourseEnrollment> courseEnrollments = new ArrayList<>();

        while (rs.next()) {
            Language language = makeLanguageFromRs(rs);
            Course course = makeCourseFromRs(rs);
            CourseGroup group = makeCourseGroupFromRs(rs, conn);
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

    public static List<Course> mapResultSetToCourses(ResultSet rs) throws SQLException {
        List<Course> courses = new ArrayList<>();

        while (rs.next()) {
            Language language = makeLanguageFromRs(rs);
            Course course = makeCourseFromRs(rs);
            course.setLanguage(language);

            courses.add(course);
        }

        return courses;
    }

    public static List<Student> mapStudents(ResultSet rs, Connection connection) throws SQLException {
        List<Student> students = new ArrayList<>();

        while (rs.next()) {
            Student temp = mapStudentFromRs(rs, connection);
            students.add(temp);
        }

        return students;
    }

    private static Course makeCourseFromRs(ResultSet rs) throws SQLException {
        Course temp = new Course();
        temp.setId(rs.getLong("course_id"));
        temp.setName(rs.getString("course_name"));
        temp.setStartDate(rs.getDate("start_date").toLocalDate());
        temp.setEndDate(rs.getDate("end_date").toLocalDate());
        temp.setGroupCapacity(rs.getInt("group_capacity"));

        return temp;
    }

    private static Language makeLanguageFromRs(ResultSet rs) throws SQLException {
        Language temp = new Language();
        temp.setId(rs.getLong("language_id"));
        temp.setName(rs.getString("language_name"));
        temp.setLevel(Level.valueOf(rs.getString("level")));

        return temp;
    }

    private static CourseGroup makeCourseGroupFromRs(ResultSet rs, Connection connection) throws SQLException {
        if (rs.getString("group_name") == null) {
            return null;
        }

        CourseGroup courseGroup = new CourseGroup();
        courseGroup.setId(rs.getLong("course_group_id"));
        courseGroup.setName(rs.getString("group_name"));
        courseGroup.setNumOfStudents(rs.getInt("number_of_students"));

        List<Tutor> tutorOfGroup = makeTutorOfGroupFromRs(rs, connection);
        courseGroup.setTutors(tutorOfGroup);

        return courseGroup;
    }

    private static List<Tutor> makeTutorOfGroupFromRs(ResultSet rs, Connection connection) throws SQLException {
        List<Tutor> tutors = new ArrayList<>();

        Long groupId = rs.getLong("course_group_id");
        Long courseId = rs.getLong("course_id");

        String sqlQuery = "SELECT t.id AS tutor_id,t.first_name AS tutor_first_name,t.last_name AS tutor_last_name FROM tutor_assignment ta\n"
                + "INNER JOIN tutor t\n"
                + "ON ta.tutor_id = t.id\n"
                + "WHERE ta.course_id = ? AND ta.course_group_id = ?;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, courseId);
            statement.setLong(2, groupId);

            ResultSet tempRs = statement.executeQuery();
            while (tempRs.next()) {
                Tutor temp = new Tutor();
                temp.setId(tempRs.getLong("tutor_id"));
                temp.setFirstName(tempRs.getString("tutor_first_name"));
                temp.setLastName(tempRs.getString("tutor_last_name"));

                tutors.add(temp);
            }
        }

        return tutors;
    }

    private static Student mapStudentFromRs(ResultSet rs, Connection conn) throws SQLException {
        Student temp = new Student(
                rs.getLong("user_id"), rs.getString("username"), rs.getString("password"),
                rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birthdate").toLocalDate(), rs.getDate("creation_date").toLocalDate());

        List<CourseEnrollment> courseEnrollments = mapFullyCourseEnrollmentsByStudent(rs, conn);
        //TO DO: map course groups

        temp.setCourseEnrollments(courseEnrollments);

        return temp;
    }

    private static List<CourseEnrollment> mapFullyCourseEnrollmentsByStudent(ResultSet rs, Connection conn) throws SQLException {
        List<CourseEnrollment> courseEnrollments = new ArrayList<>();

        Long studentId = rs.getLong("user_id");

        String sqlQuery = "SELECT s.user_id,ce.course_id,ce.enrollment_date,\n"
                + "c.name AS course_name,c.start_date,c.end_date,c.group_capacity,c.language_id,l.name AS language_name,l.level\n"
                + "FROM student s\n"
                + "LEFT JOIN course_enrollment ce\n"
                + "ON s.user_id=ce.student_id\n"
                + "LEFT JOIN course c\n"
                + "ON ce.course_id = c.id\n"
                + "LEFT JOIN `language` l\n"
                + "ON c.language_id = l.id\n"
                + "WHERE s.user_id = ?;";

        try ( PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setLong(1, studentId);

            ResultSet tempRs = statement.executeQuery();
            while (tempRs.next()) {
                if (tempRs.getDate("enrollment_date") == null) //if student haven't enrolled any course yet
                {
                    return courseEnrollments;
                }

                CourseEnrollment tempCourseEnrollment = new CourseEnrollment();
                tempCourseEnrollment.setEnrollmentDate(tempRs.getDate("enrollment_date").toLocalDate());
                Course tempCourse = makeCourseFromRs(tempRs);
                Language tempLanguage = makeLanguageFromRs(tempRs);
                tempCourse.setLanguage(tempLanguage);
                
                tempCourseEnrollment.setCourse(tempCourse);
                courseEnrollments.add(tempCourseEnrollment);
            }
        }

        return courseEnrollments;
    }

}
