/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package validation.builder;

/**
 *
 * @author ivano
 */
public interface RuleBuilder {
 
    
        RuleBuilder maxLength(int maxLength);
        RuleBuilder minLength(int minLength);
        RuleBuilder notEmpty();
        RuleBuilder matchesRegex(String regex);
        RuleBuilder withMessage(String message);
}
