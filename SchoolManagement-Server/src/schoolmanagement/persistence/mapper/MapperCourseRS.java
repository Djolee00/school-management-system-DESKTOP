/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.mapper;

import java.util.List;
import schoolmanagement.commonlib.model.Course;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import schoolmanagement.commonlib.model.Language;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.commonlib.model.enums.Level;

/**
 *
 * @author ivano
 */
public class MapperCourseRS {

    public static List<Course> mapCourseWithLanguage(ResultSet rs) throws SQLException {
        List<Course> courses = new ArrayList<>();

        while (rs.next()) {
            Language tempLanguage = new Language(rs.getString("language_name"), Level.valueOf(rs.getString("level")));
            tempLanguage.setId(rs.getLong("language_id"));
            Course tempCourse = new Course();
            tempCourse.setId(rs.getLong("course_id"));
            tempCourse.setName(rs.getString("course_name"));
            tempCourse.setStartDate(rs.getDate("start_date").toLocalDate());
            tempCourse.setEndDate(rs.getDate("end_date").toLocalDate());
            tempCourse.setGroupCapacity(rs.getInt("group_capacity"));
            tempCourse.setLanguage(tempLanguage);
            
            courses.add(tempCourse);
        }
        return courses;
    }

    public static List<Student> mapStudentsOfCourse(ResultSet rs) throws SQLException {
        List<Student> students = new ArrayList<>();
        while(rs.next()){
            Student temp = new Student(rs.getString("username"), rs.getString("password"));
            temp.setId(rs.getLong("id"));
            temp.setFirstName(rs.getString("first_name"));
            temp.setLastName(rs.getString("last_name"));
            temp.setBirthdate(rs.getDate("birthdate").toLocalDate());
            temp.setCreationDate(rs.getDate("creation_date").toLocalDate());
            students.add(temp);
        }
        return students;
    }
}
