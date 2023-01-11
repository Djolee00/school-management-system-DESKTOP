/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.service;

import java.io.IOException;
import java.sql.SQLException;
import schoolmanagement.commonlib.model.User;

/**
 *
 * @author ivano
 */
public interface UserService {
    
    User login(String username,String password) throws IOException, SQLException;
}
