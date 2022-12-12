/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaton.rule;

/**
 *
 * @author ivano
 */
public class MinLengthRule extends Rule {

    private int minLength;
    private Object property;

    public MinLengthRule(int maxLength, Object property) {
        this.minLength = maxLength;
        this.property = property;
    }

    @Override
    public String validate() {
        if (property != null) {
            if (property instanceof String temp) {

                if (temp.length() < minLength) {
                    if (this.getErrorMessage() != null && !this.getErrorMessage().isEmpty()) {
                        return this.getErrorMessage();
                    } else {
                        return "String can't be shorter than " + minLength + " characters!";
                    }

                }
            }
        }
        return null;
    }
}
