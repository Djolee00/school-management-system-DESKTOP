/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import schoolmanagement.server.handler.ClientHandler;

/**
 *
 * @author ivano
 */
public class Server extends Thread {

    private final ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(9000);
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Connected!");
                handleClient(socket);
            } catch (IOException ex) {
                System.out.println("Server is down!");
            }
        }
    }

    private void handleClient(Socket socket) {
        try {
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        } catch (IOException ex) {
            System.out.println("Error while opening in and out streams!");
        }
    }

}
