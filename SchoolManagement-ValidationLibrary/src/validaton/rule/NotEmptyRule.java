/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaton.rule;

/**
 *
 * @author ivano
 */
public class NotEmptyRule extends Rule {

    private Object property;

    public NotEmptyRule(Object property) {
        this.property = property;
    }

    @Override
    public String validate() {
        if (property == null || (property instanceof String && ((String)property).isBlank())){
            if(this.getErrorMessage()!=null && !this.getErrorMessage().isEmpty())
                return this.getErrorMessage();
            else
                return "String can't be empty!";
        }
        return null;
    }
}
