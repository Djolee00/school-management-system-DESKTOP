/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaton.rule;

/**
 *
 * @author ivano
 */
public class MaxLengthRule extends Rule {

    private int maxLength;
    private Object property;

    public MaxLengthRule(int maxLength, Object property) {
        this.maxLength = maxLength;
        this.property = property;
    }

    @Override
    public String validate() {
        if (property != null) {
            if (property instanceof String temp) {

                if (temp.length() > maxLength) {
                    if (this.getErrorMessage() != null && !this.getErrorMessage().isEmpty()) {
                        return this.getErrorMessage();
                    } else {
                        return "String can't be longer than " + maxLength+" characters!";
                    }

                }
            }
        }
        return null;

    }
}
