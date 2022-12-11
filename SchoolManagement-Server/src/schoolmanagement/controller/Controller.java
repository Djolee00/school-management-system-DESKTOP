/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.controller;

import java.util.List;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.service.StudentService;
import schoolmanagement.service.provider.ServiceProvider;

/**
 *
 * @author ivano
 */
public class Controller {

    
    public List<Student> getStudents(){
        StudentService studentService =(StudentService) ServiceProvider.getInstance().getRequiredService(StudentService.class);
        return  studentService.getAll();
    }
    
    
}
