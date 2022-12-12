/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaton.rule;

/**
 *
 * @author ivano
 */
public class MatchRegexRule extends Rule{

    private Object property;
    private String regex;

    public  MatchRegexRule(Object property,String regex) {
        this.property = property;
        this.regex = regex;
    }

    @Override
    public String validate() {
        if (property != null) {
            if (property instanceof String temp) {

                if (!temp.matches(regex)) {
                    if (this.getErrorMessage() != null && !this.getErrorMessage().isEmpty()) {
                        return this.getErrorMessage();
                    } else {
                        return "Invalid string format";
                    }

                }
            }
        }
        return null;
    }
    
}
