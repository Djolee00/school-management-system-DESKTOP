/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseGroup;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.commonlib.model.Tutor;

/**
 *
 * @author ivano
 */
public class MapperCourseGroupRS {

    public static List<CourseGroup> mapPlainCourseGroups(Course course, ResultSet rs) throws SQLException {
        List<CourseGroup> groups = new ArrayList<>();
        while (rs.next()) {
            CourseGroup temp = new CourseGroup();
            temp.setCourse(course);
            temp.setId(rs.getLong("course_group_id"));
            temp.setName(rs.getString("group_name"));
            temp.setNumOfStudents(rs.getInt("num_of_students"));

            groups.add(temp);
        }
        return groups;
    }

    public static void mapStudentsInCourseGroup(CourseGroup group, ResultSet rsStudents) throws SQLException {
        List<Student> students = new ArrayList<>();
        while (rsStudents.next()) {
            Student temp = new Student(rsStudents.getString("username"), rsStudents.getString("password"));
            temp.setId(rsStudents.getLong("student_id"));
            temp.setBirthdate(rsStudents.getDate("birthdate").toLocalDate());
            temp.setCreationDate(rsStudents.getDate("creation_date").toLocalDate());
            temp.setFirstName(rsStudents.getString("first_name"));
            temp.setLastName(rsStudents.getString("last_name"));
            students.add(temp);
        }
        group.setStudents(students);
    }

    public static void mapTutorsInCourseGroup(CourseGroup group, ResultSet rsTutors) throws SQLException {
        List<Tutor> tutors = new ArrayList<>();
        while (rsTutors.next()) {
            Tutor temp = new Tutor();
            temp.setId(rsTutors.getLong("tutor_id"));
            temp.setFirstName(rsTutors.getString("first_name"));
            temp.setLastName(rsTutors.getString("last_name"));
            
            tutors.add(temp);
        }
        group.setTutors(tutors);
    }

}
