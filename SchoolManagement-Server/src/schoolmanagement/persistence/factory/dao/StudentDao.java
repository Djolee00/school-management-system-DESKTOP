/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.persistence.factory.dao;

import java.io.IOException;
import java.util.List;
import schoolmanagement.commonlib.model.Student;
import java.sql.*;

/**
 *
 * @author ivano
 */
public interface StudentDao {
    
    Student saveStudent(Student student) throws SQLException,IOException ;
    void updateStudent();
    List<Student> getAllStudents();
    Student getStudentById(Long id);
    
    
}
