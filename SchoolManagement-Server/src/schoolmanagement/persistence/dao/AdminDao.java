/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.persistence.dao;

import schoolmanagement.commonlib.model.User;
import java.sql.SQLException;

/**
 *
 * @author ivano
 */
public interface AdminDao extends DaoInterface{

    public User getAdminByUser(User user) throws SQLException;
    
}
