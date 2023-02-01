/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import schoolmanagement.config.Configuration;
import schoolmanagement.server.handler.ClientHandler;

/**
 *
 * @author ivano
 */
public class Server extends Thread {

    private final ServerSocket serverSocket;
    public static List<ClientHandler> loggedClients;

    public Server() throws IOException {
        loggedClients = new ArrayList<>();
        serverSocket = new ServerSocket(readPortNumber());
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

    private int readPortNumber() throws FileNotFoundException, IOException {
        Properties properties = new Properties();

        try ( FileInputStream input = new FileInputStream(Configuration.CONFIG_FILE_PATH)) {
            properties.load(input);
            return Integer.parseInt(properties.getProperty(Configuration.SERVER_PORT));
        }
    }

}
