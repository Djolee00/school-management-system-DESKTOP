/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import schoolmanagement.commonlib.model.Language;
import schoolmanagement.commonlib.model.Tutor;

/**
 *
 * @author ivano
 */
public interface LanguageService {

    public List<Language> getAllLanguages() throws IOException,SQLException;

    public List<Tutor> getAllTutors(Language language) throws IOException,SQLException;
    
}
