/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import schoolmanagement.commonlib.model.Language;
import schoolmanagement.commonlib.model.enums.Level;

/**
 *
 * @author ivano
 */
public class MapperLanguageRS {

    public static List<Language> mapLanguages(ResultSet rs) throws SQLException {
        List<Language> languages = new ArrayList<>();
        while(rs.next()){
             Language temp = new Language();
             temp.setId(rs.getLong("id"));
             temp.setName(rs.getString("name"));
             temp.setLevel(Level.valueOf(rs.getString("level")));
             
             languages.add(temp);
        }
        
        return languages;
    }
    
}
