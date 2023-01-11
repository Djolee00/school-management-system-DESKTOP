/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.commonlib.communication;

import java.io.Serializable;

/**
 *
 * @author ivano
 */
public class Request implements Serializable{
    
    private Operation operation;
    private Object object;

    public Request(Operation operation, Object object) {
        this.operation = operation;
        this.object = object;
    }
    
    
    public Request() {
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Request{" + "operation=" + operation + ", object=" + object + '}';
    }
    
    
}
