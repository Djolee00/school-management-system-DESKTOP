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
public class Response implements Serializable{
    
    private Object object;
    private ResponseType responseType;

    public Response(Object object, ResponseType responseType) {
        this.object = object;
        this.responseType = responseType;
    }

    public Response() {
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    @Override
    public String toString() {
        return "Response{" + "object=" + object + ", responseType=" + responseType + '}';
    }
    
}
