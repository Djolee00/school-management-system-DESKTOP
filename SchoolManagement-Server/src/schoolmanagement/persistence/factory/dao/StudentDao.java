/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.persistence.factory.dao;

import java.util.List;
import schoolmanagement.commonlib.model.Student;

/**
 *
 * @author ivano
 */
public interface StudentDao {
    
    void save();
    void update();
    List<Student> getAll();
    Student getById(Long id);
    
    
}
