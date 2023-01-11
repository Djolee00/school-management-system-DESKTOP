/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.persistence.dao;

import java.io.IOException;
import java.util.List;
import schoolmanagement.commonlib.model.Student;
import java.sql.SQLException;
import schoolmanagement.commonlib.model.User;

/**
 *
 * @author ivano
 */
public interface StudentDao extends DaoInterface{

    Student saveStudent(Student student) throws SQLException, IOException;

    void updateStudent();

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    public Student getStudentByUser(User user) throws SQLException;


}
