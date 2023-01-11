/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.server.handler;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import schoolmanagement.commonlib.communication.Sender;
import schoolmanagement.commonlib.communication.Receiver;
import schoolmanagement.commonlib.communication.Request;
import schoolmanagement.commonlib.communication.Response;
import schoolmanagement.commonlib.communication.ResponseType;
import schoolmanagement.commonlib.model.User;
import schoolmanagement.server.Server;
import schoolmanagement.service.UserService;
import schoolmanagement.service.provider.ServiceProvider;

/**
 *
 * @author ivano
 */
public class ClientHandler extends Thread {

    private Socket socket;
    private Sender sender;
    private Receiver receiver;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
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
                loginUser(request, response);
            }
        }

        sender.send(response);

    }

    private void loginUser(Request request, Response response) throws SQLException, IOException {
        User temp = (User) request.getObject();
        User user = ((UserService) ServiceProvider.getInstance().getRequiredService(UserService.class)).login(temp.getUsername(), temp.getPassword());
        if (user == null) {
            response.setResponseType(ResponseType.FAILURE);
        } else {
            response.setResponseType(ResponseType.SUCCESS);
            response.setObject(user);
        }
    }

}
