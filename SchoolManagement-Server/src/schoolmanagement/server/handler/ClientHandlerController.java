/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.server.handler;

import java.io.IOException;
import java.sql.SQLException;
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
public class ClientHandlerController {

    public Response loginUser(Request request) throws IOException, SQLException {
        Response response = new Response();
        User temp = (User) request.getObject();
        User user = ((UserService) ServiceProvider.getInstance().getRequiredService(UserService.class)).login(temp.getUsername(), temp.getPassword());
        if (user == null) {
            response.setResponseType(ResponseType.FAILURE);
        } else {
            response.setResponseType(ResponseType.SUCCESS);
            response.setObject(user);
        }
        
        return response;
    }
    
    
    
}
