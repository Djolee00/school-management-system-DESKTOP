/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.validator.builder;

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
                .withMessage("Morate uneti korisnicko ime")
                .minLength(5)
                .withMessage("Korisnicko ime mora imati najmanje 5 karaktera")
                .maxLength(15)
                .withMessage("Korisnicko ne sme imati više od 15 karaktera");
        
        ruleFor(student.getPassword())
                .notEmpty()
                .withMessage("Morate uneti sifru")
                .minLength(8)
                .withMessage("Sifra mora imati najmanje 8 karaktera")
                .maxLength(20)
                .withMessage("Sifra ne sme imati više od 20 karaktera")
                .matchesRegex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
                .withMessage("Sifra mora da sadrzi makar 1 malo slovo, 1 veliko slovo i 1 specijalan karakter");
        
        ruleFor(student.getFirstName())
                .notEmpty()
                .withMessage("Morate uneti  ime")
                .matchesRegex("^[A-Za-z]+(((\\'|\\-|\\.)?([A-Za-z])+))?$")
                .withMessage("Ime sme da sadrzi samo slova i karaktere (',-,.)");
        
        ruleFor(student.getLastName())
                .notEmpty()
                .withMessage("Morate uneti  prezime")
                .matchesRegex("^[A-Za-z]+(((\\'|\\-|\\.)?([A-Za-z])+))?$")
                .withMessage("Ime sme da sadrzi samo slova i karaktere (',-,.)");
  
    }
    
}
