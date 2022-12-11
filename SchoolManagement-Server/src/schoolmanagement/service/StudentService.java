/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.service;

import java.util.List;
import schoolmanagement.commonlib.model.Student;

/**
 *
 * @author ivano
 */
public interface StudentService {
    
    void save();
    void update();
    Student getById();
    List<Student> getAll();
}
