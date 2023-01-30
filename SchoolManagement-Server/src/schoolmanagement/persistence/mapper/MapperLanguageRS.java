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
import schoolmanagement.commonlib.model.Tutor;
import schoolmanagement.commonlib.model.enums.Level;

/**
 *
 * @author ivano
 */
public class MapperLanguageRS {

    public static List<Language> mapLanguages(ResultSet rs) throws SQLException {
        List<Language> languages = new ArrayList<>();
        while (rs.next()) {
            Language temp = new Language();
            temp.setId(rs.getLong("id"));
            temp.setName(rs.getString("name"));
            temp.setLevel(Level.valueOf(rs.getString("level")));

            languages.add(temp);
        }

        return languages;
    }

    public static List<Tutor> mapTutorsOfLanguage(ResultSet rs) throws SQLException {
        List<Tutor> tutors = new ArrayList<>();
        while (rs.next()) {
            Tutor temp = new Tutor();
            temp.setId(rs.getLong("tutor_id"));
            temp.setFirstName(rs.getString("first_name"));
            temp.setLastName(rs.getString("last_name"));
            tutors.add(temp);
        }
        return tutors;
    }

}
