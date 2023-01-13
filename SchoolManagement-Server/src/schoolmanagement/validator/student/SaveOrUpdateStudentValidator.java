/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.validator.student;

import java.sql.SQLException;
import schoolmanagement.commonlib.model.Student;
import schoolmanagement.persistence.dao.UserDao;
import schoolmanagement.validator.builder.StudentValidatorBuilder;
import validation.exception.ValidationException;
import validaton.rule.result.ResultInfo;

/**
 *
 * @author ivano
 */
public class SaveOrUpdateStudentValidator implements StudentValidator{

    @Override
    public void  validate(Student student,UserDao userDao) throws ValidationException,SQLException {
        validateStudentData(student);
        
        validateStudentUsername(student.getUsername(), userDao);
    }
    
    private void validateStudentData(Student student) throws ValidationException{
        ResultInfo result = new StudentValidatorBuilder(student).validate();
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
    }
    
    private void validateStudentUsername(String username,UserDao userDao) throws SQLException, ValidationException {
        boolean isUnique = userDao.isUsernameUnique(username);
        if (isUnique == false) {
            throw new ValidationException("Username already exists in system!");
        }
    }
}
