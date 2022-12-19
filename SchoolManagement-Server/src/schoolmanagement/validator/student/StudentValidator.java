/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.validator.student;

import java.sql.SQLException;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.persistence.dao.UserDao;
import validation.exception.ValidationException;

/**
 *
 * @author ivano
 */
public interface StudentValidator {
    
    void validate(Student student,UserDao userDao) throws ValidationException,SQLException;
}
