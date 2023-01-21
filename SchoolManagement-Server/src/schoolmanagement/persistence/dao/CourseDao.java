/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.persistence.dao;

import java.sql.SQLException;
import java.util.List;
import schoolmanagement.commonlib.model.Course;

/**
 *
 * @author ivano
 */
public interface CourseDao extends DaoInterface{

    public List<Course> getAllCourses() throws SQLException;
    
}