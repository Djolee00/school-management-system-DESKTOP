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
import schoolmanagement.persistence.dao.LanguageDao;
import schoolmanagement.persistence.mapper.MapperCourseRS;
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

}
