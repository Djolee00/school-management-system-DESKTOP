/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.persistence.dao;

import java.sql.SQLException;
import java.util.List;
import schoolmanagement.commonlib.model.Course;
import schoolmanagement.commonlib.model.CourseGroup;

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

    public List<CourseGroup> getGroupsOfCourse(Course temp)  throws SQLException;
    
}
