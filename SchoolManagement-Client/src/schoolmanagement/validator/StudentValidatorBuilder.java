/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.validator;

import schoolmanagement.commonlib.model.Student;
import validation.abstraction.AbstractValidator;

/**
 *
 * @author ivano
 */

// this is an usage of my Validator Library which is based on Builder pattern, it is used for validation of input data
public class StudentValidatorBuilder extends AbstractValidator {
    
    public StudentValidatorBuilder(Student student) {
        ruleFor(student.getUsername())
                .notEmpty()
                .withMessage("You have to insert username")
                .minLength(5)
                .withMessage("Username must have at least 5 characters")
                .maxLength(15)
                .withMessage("Username can't have more than 15 characters");
        
        ruleFor(student.getPassword())
                .notEmpty()
                .withMessage("You have to insert password")
                .minLength(8)
                .withMessage("Password must have at least 8 characters")
                .maxLength(20)
                .withMessage("Password can't have more than 20 characters")
                .matchesRegex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
                .withMessage("Password must have at least 1 lowercase, 1 uppercase and 1 special character");
        
        ruleFor(student.getFirstName())
                .notEmpty()
                .withMessage("You have to insert first name")
                .matchesRegex("^[A-Za-z]+(((\\'|\\-|\\.)?([A-Za-z])+))?$")
                .withMessage("First name can have only characters and these special characters (',-,.)");
        
        ruleFor(student.getLastName())
                .notEmpty()
                .withMessage("You have to insert last name")
                .matchesRegex("^[A-Za-z]+(((\\'|\\-|\\.)?([A-Za-z])+))?$")
                .withMessage("Last name can have only characters and these special characters (',-,.)");
    }
    
}
