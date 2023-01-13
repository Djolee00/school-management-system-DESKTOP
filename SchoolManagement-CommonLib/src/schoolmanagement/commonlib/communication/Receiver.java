/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.commonlib.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author ivano
 */
public class Receiver {
    
    private final ObjectInputStream in;
    
    public Receiver(Socket socket) throws IOException{
        in = new ObjectInputStream(socket.getInputStream());
    }
    
   public Object receive() throws IOException, ClassNotFoundException {
       return in.readObject();
    }
    
}
