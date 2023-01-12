/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.server.handler;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import schoolmanagement.commonlib.communication.Sender;
import schoolmanagement.commonlib.communication.Receiver;
import schoolmanagement.commonlib.communication.Request;
import schoolmanagement.commonlib.communication.Response;
import schoolmanagement.commonlib.communication.ResponseType;
import schoolmanagement.commonlib.model.User;
import schoolmanagement.service.UserService;
import schoolmanagement.service.provider.ServiceProvider;

/**
 *
 * @author ivano
 */
public class ClientHandler extends Thread {

    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;
    private final ClientHandlerController controller;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
        controller = new ClientHandlerController();
    }

    @Override
    public void run() {
        try {
            while (true) {

                Request request = (Request) receiver.receive();
                handleRequest(request);

            }
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            System.out.println("One client disconnected");
        }
    }

    private void handleRequest(Request request) throws SQLException, IOException {
        Response response = new Response();

        switch (request.getOperation()) {
            case LOGIN -> {
                response = controller.loginUser(request);
            }
        }
        
        sender.send(response);
    }

}
