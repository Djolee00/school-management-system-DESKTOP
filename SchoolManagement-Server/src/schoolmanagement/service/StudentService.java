/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.service;

import java.io.IOException;
import java.util.List;
import schoolmanagement.commonlib.model.Student;
import validation.exception.ValidationException;
import java.sql.SQLException;

/**
 *
 * @author ivano
 */
public interface StudentService {
    
    Student save(Student student) throws ValidationException,IOException,SQLException;
    void update();
    Student getById();
    List<Student> getAll();
}
