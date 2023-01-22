/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.commonlib.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author ivano
 */
public class Sender {
    
    private final ObjectOutputStream out;
    
    public Sender(Socket socket) throws IOException{
        out = new ObjectOutputStream(socket.getOutputStream());
    }
    
    public void send(Object object) throws IOException {
        out.writeObject(object);
        out.flush();
        out.reset();
    }
}
