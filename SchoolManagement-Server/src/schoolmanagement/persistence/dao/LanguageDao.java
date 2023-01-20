/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.persistence.dao;

import java.sql.SQLException;
import java.util.List;
import schoolmanagement.commonlib.model.Language;

/**
 *
 * @author ivano
 */
public interface LanguageDao extends DaoInterface{

    public List<Language> getAllLanguages() throws SQLException;
    
}
