/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation.exception;

import java.util.List;

/**
 *
 * @author ivano
 */
public class ValidationException extends Exception{

    public ValidationException(List<String> errors) {
        super(String.join( "\n",errors));
    }
    
     public ValidationException(String error) {
        super(error);
    }
    
    
}
