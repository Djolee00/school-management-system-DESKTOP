/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.persistence.dao;

import java.sql.SQLException;
import java.util.List;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseGroup;
import schoolmanagement.commonlib.model.Student;

/**
 *
 * @author ivano
 */
public interface CourseDao extends DaoInterface{

    public List<Course> getAllCourses() throws SQLException;

    public boolean updateCourse(Course course) throws SQLException;

    public boolean checkIfCourseGroupsExist(Course course) throws SQLException;

    public void deleteCourseEnrollments(Course course) throws SQLException;

    public void deleteCourse(Course course) throws SQLException;

    public Long saveCourse(Course course) throws SQLException;

    public List<CourseGroup> getGroupsOfCourse(Course temp) throws SQLException;

    public List<Student> getStudentsOfCourse(Course temp) throws SQLException;

    public Long saveCourseGroup(CourseGroup courseGroup) throws SQLException;

    public boolean updateCourseGroupData(CourseGroup courseGroup) throws SQLException;

    public void deleteTutorsFromGroup(CourseGroup courseGroup) throws SQLException;

    public void deleteStudentsFromGroup(CourseGroup courseGroup) throws SQLException;

    public void addStudentsInGroup(CourseGroup courseGroup) throws SQLException;

    public void addTutorsInGroup(CourseGroup courseGroup) throws SQLException;
    
}
