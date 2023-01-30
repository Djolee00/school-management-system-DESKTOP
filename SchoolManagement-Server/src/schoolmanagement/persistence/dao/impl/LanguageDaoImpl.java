/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.persistence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import schoolmanagement.commonlib.model.Language;
import schoolmanagement.commonlib.model.Tutor;
import schoolmanagement.persistence.dao.LanguageDao;
import schoolmanagement.persistence.mapper.MapperLanguageRS;

/**
 *
 * @author ivano
 */
public class LanguageDaoImpl implements LanguageDao {

    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Language> getAllLanguages() throws SQLException {
        final String sqlQuery = "SELECT *  FROM `language`;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet rs = statement.executeQuery();
            return MapperLanguageRS.mapLanguages(rs);
        }
    }

    @Override
    public List<Tutor> getAllTutors(Language language) throws SQLException {
        final String sqlQuery = "SELECT t.id AS tutor_id, t.first_name,t.last_name FROM `language` l\n"
                + "	INNER JOIN language_tutor lt\n"
                + "	ON lt.language_id = l.id\n"
                + "	INNER JOIN tutor t\n"
                + "	ON lt.tutor_id = t.id\n"
                + "	WHERE l.id = ?;";

        try ( PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, language.getId());
            ResultSet rs = statement.executeQuery();
            return MapperLanguageRS.mapTutorsOfLanguage(rs);
        }
    }

}
